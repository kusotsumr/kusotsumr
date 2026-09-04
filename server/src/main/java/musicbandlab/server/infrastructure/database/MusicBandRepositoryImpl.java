package musicbandlab.server.infrastructure.database;

import musicbandlab.common.contracts.DataEntry;
import musicbandlab.common.domain.Coordinates;
import musicbandlab.common.domain.Label;
import musicbandlab.common.domain.MusicBand;
import musicbandlab.common.domain.MusicGenre;
import musicbandlab.server.core.ports.MusicBandRepository;

import java.sql.*;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

public final class MusicBandRepositoryImpl implements MusicBandRepository {
    private final ConnectionManager connectionManager;
    private final Map<String, MusicBand> map = Collections.synchronizedMap(new HashMap<>());
    private final ZonedDateTime initializationDate = ZonedDateTime.now();

    public MusicBandRepositoryImpl(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public static MusicBandRepositoryImpl loadFromDatabase(ConnectionManager connectionManager) throws SQLException {
        MusicBandRepositoryImpl repo = new MusicBandRepositoryImpl(connectionManager);

        String sql = "SELECT id, band_key, name, coord_x, coord_y, creation_date, " +
                "number_of_participants, albums_count, genre, label_bands, owner_login FROM music_bands";

        try (Connection conn = connectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String key = rs.getString("band_key");
                repo.map.put(key, mapRow(rs));
            }
        }

        return repo;
    }

    private static MusicBand mapRow(ResultSet rs) throws SQLException {
        Coordinates coordinates = new Coordinates(rs.getInt("coord_x"), rs.getDouble("coord_y"));

        Integer labelBands = (Integer) rs.getObject("label_bands");
        Label label = new Label(labelBands);

        String genreStr = rs.getString("genre");
        MusicGenre genre = genreStr == null ? null : MusicGenre.valueOf(genreStr);

        MusicBand band = new MusicBand(
                rs.getString("name"),
                coordinates,
                rs.getLong("number_of_participants"),
                rs.getLong("albums_count"),
                genre,
                label,
                rs.getInt("id")
        );

        OffsetDateTime creationDate = rs.getObject("creation_date", OffsetDateTime.class);
        band.setCreationDate(creationDate.atZoneSameInstant(ZoneId.systemDefault()));
        band.setOwnerLogin(rs.getString("owner_login"));

        return band;
    }

    @Override
    public ArrayList<DataEntry<String, MusicBand>> getAll(int page, int pageSize) {
        synchronized (map) {
            return map.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue())
                    .skip((long) (page - 1) * pageSize)
                    .limit(pageSize)
                    .map(entry -> new DataEntry<>(entry.getKey(), entry.getValue()))
                    .collect(Collectors.toCollection(ArrayList::new));
        }
    }

    @Override
    public MusicBand get(String key) {
        throwIfKeyIsNullOrEmpty(key);
        return map.get(key);
    }

    @Override
    public void insert(String key, MusicBand musicBand) {
        throwIfKeyIsNullOrEmpty(key);
        Objects.requireNonNull(musicBand, "musicBand");
        Objects.requireNonNull(musicBand.getOwnerLogin(), "musicBand.ownerLogin");

        synchronized (map) {
            if (map.containsKey(key)) {
                throw new IllegalArgumentException("Key already exists");
            }
        }

        String sql = "INSERT INTO music_bands " +
                "(band_key, name, coord_x, coord_y, creation_date, number_of_participants, albums_count, genre, label_bands, owner_login) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?) RETURNING id";

        try (Connection conn = connectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, key);
            setBandParameters(ps, 2, musicBand);
            ps.setString(10, musicBand.getOwnerLogin());

            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                musicBand.setId(rs.getInt("id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to insert music band into database", e);
        }

        map.put(key, musicBand);
    }

    @Override
    public void updateWhereIdIsEqualTo(int id, MusicBand musicBand, String currentUserLogin) {
        Objects.requireNonNull(musicBand, "musicBand");

        MusicBand existing;
        synchronized (map) {
            existing = map.values().stream()
                    .filter(band -> band.getId() == id)
                    .findFirst()
                    .orElse(null);
        }

        if (existing == null) {
            throw new IllegalArgumentException("There are no music bands with id " + id);
        }
        throwIfNotOwner(existing, currentUserLogin);

        String sql = "UPDATE music_bands SET name=?, coord_x=?, coord_y=?, creation_date=?, " +
                "number_of_participants=?, albums_count=?, genre=?, label_bands=? WHERE id=?";

        try (Connection conn = connectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            setBandParameters(ps, 1, musicBand);
            ps.setInt(9, id);

            int updated = ps.executeUpdate();
            if (updated == 0) {
                throw new RuntimeException("Music band with id " + id + " not found in database");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update music band in database", e);
        }

        synchronized (map) {
            existing.mapFrom(musicBand);
        }
    }

    @Override
    public void remove(String key, String currentUserLogin) {
        throwIfKeyIsNullOrEmpty(key);

        MusicBand existing = map.get(key);
        if (existing == null) {
            return;
        }
        throwIfNotOwner(existing, currentUserLogin);

        String sql = "DELETE FROM music_bands WHERE band_key = ?";

        try (Connection conn = connectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, key);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to remove music band from database", e);
        }

        map.remove(key);
    }

    @Override
    public void clear(String currentUserLogin) {
        Objects.requireNonNull(currentUserLogin, "currentUserLogin");

        String sql = "DELETE FROM music_bands WHERE owner_login = ?";

        try (Connection conn = connectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, currentUserLogin);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to clear music bands in database", e);
        }

        synchronized (map) {
            map.entrySet().removeIf(entry -> currentUserLogin.equals(entry.getValue().getOwnerLogin()));
        }
    }

    @Override
    public void removeWhereLessThan(MusicBand musicBand, String currentUserLogin) {
        Objects.requireNonNull(musicBand, "musicBand");
        Objects.requireNonNull(currentUserLogin, "currentUserLogin");

        List<String> keysToRemove = new ArrayList<>();
        List<Integer> idsToRemove = new ArrayList<>();

        synchronized (map) {
            for (Map.Entry<String, MusicBand> entry : map.entrySet()) {
                MusicBand band = entry.getValue();
                if (currentUserLogin.equals(band.getOwnerLogin()) && band.compareTo(musicBand) < 0) {
                    keysToRemove.add(entry.getKey());
                    idsToRemove.add(band.getId());
                }
            }
        }

        if (idsToRemove.isEmpty()) {
            return;
        }

        String sql = "DELETE FROM music_bands WHERE id = ANY(?)";

        try (Connection conn = connectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            Array idsArray = conn.createArrayOf("integer", idsToRemove.toArray());
            ps.setArray(1, idsArray);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to remove music bands from database", e);
        }

        synchronized (map) {
            keysToRemove.forEach(map::remove);
        }
    }

    @Override
    public Boolean replaceIfGreaterThan(String key, MusicBand musicBand, String currentUserLogin) {
        return replaceIf(key, musicBand, currentUserLogin, cmp -> cmp > 0);
    }

    @Override
    public Boolean replaceIfLessThan(String key, MusicBand musicBand, String currentUserLogin) {
        return replaceIf(key, musicBand, currentUserLogin, cmp -> cmp < 0);
    }

    private Boolean replaceIf(String key, MusicBand musicBand, String currentUserLogin, java.util.function.IntPredicate condition) {
        throwIfKeyIsNullOrEmpty(key);
        Objects.requireNonNull(musicBand, "musicBand");

        MusicBand existing = map.get(key);
        if (existing == null || !condition.test(musicBand.compareTo(existing))) {
            return false;
        }
        throwIfNotOwner(existing, currentUserLogin);

        String sql = "UPDATE music_bands SET name=?, coord_x=?, coord_y=?, creation_date=?, " +
                "number_of_participants=?, albums_count=?, genre=?, label_bands=? WHERE id=?";

        try (Connection conn = connectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            setBandParameters(ps, 1, musicBand);
            ps.setInt(9, existing.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to replace music band in database", e);
        }

        synchronized (map) {
            existing.mapFrom(musicBand);
        }
        return true;
    }

    @Override
    public int getCountWhereNumberOfParticipantsEqualsTo(long numberOfParticipants) {
        if (numberOfParticipants <= 0) {
            return 0;
        }
        synchronized (map) {
            return (int) map.values().stream()
                    .filter(Objects::nonNull)
                    .filter(band -> band.getNumberOfParticipants() == numberOfParticipants)
                    .count();
        }
    }

    @Override
    public ArrayList<MusicBand> getWhereLabelLessThan(Label label, int page, int pageSize) {
        if (label == null) {
            return new ArrayList<>();
        }
        synchronized (map) {
            return map.values().stream()
                    .filter(Objects::nonNull)
                    .filter(band -> band.getLabel() != null)
                    .filter(band -> band.getLabel().compareTo(label) < 0)
                    .sorted()
                    .skip((long) (page - 1) * pageSize)
                    .limit(pageSize)
                    .collect(Collectors.toCollection(ArrayList::new));
        }
    }

    @Override
    public ArrayList<Label> getLabelsDescending(int page, int pageSize) {
        synchronized (map) {
            return map.values().stream()
                    .filter(Objects::nonNull)
                    .map(MusicBand::getLabel)
                    .filter(Objects::nonNull)
                    .filter(label -> label.getBands() != null)
                    .distinct()
                    .sorted(Comparator.reverseOrder())
                    .skip((long) (page - 1) * pageSize)
                    .limit(pageSize)
                    .collect(Collectors.toCollection(ArrayList::new));
        }
    }

    @Override
    public int getSize() {
        return map.size();
    }

    @Override
    public ZonedDateTime getInitializationDate() {
        return initializationDate;
    }

    @Override
    public String serializeToJson() {
        throw new UnsupportedOperationException("File-based persistence was removed in lab 7");
    }

    private void setBandParameters(PreparedStatement ps, int startIndex, MusicBand musicBand) throws SQLException {
        int i = startIndex;
        ps.setString(i++, musicBand.getName());
        ps.setInt(i++, musicBand.getCoordinates().getX());
        ps.setDouble(i++, musicBand.getCoordinates().getY());
        ps.setObject(i++, musicBand.getCreationDate().toOffsetDateTime());
        ps.setLong(i++, musicBand.getNumberOfParticipants());
        ps.setLong(i++, musicBand.getAlbumsCount());

        if (musicBand.getGenre() != null) {
            ps.setString(i++, musicBand.getGenre().name());
        } else {
            ps.setNull(i++, Types.VARCHAR);
        }

        if (musicBand.getLabel() != null && musicBand.getLabel().getBands() != null) {
            ps.setInt(i++, musicBand.getLabel().getBands());
        } else {
            ps.setNull(i++, Types.INTEGER);
        }
    }

    private void throwIfNotOwner(MusicBand band, String currentUserLogin) {
        Objects.requireNonNull(currentUserLogin, "currentUserLogin");
        if (!currentUserLogin.equals(band.getOwnerLogin())) {
            throw new SecurityException("Вы можете изменять только свои объекты");
        }
    }

    private void throwIfKeyIsNullOrEmpty(String key) {
        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException("Key should be not empty");
        }
    }
}