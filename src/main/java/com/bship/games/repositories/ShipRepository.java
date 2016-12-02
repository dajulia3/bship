package com.bship.games.repositories;

import com.bship.games.domains.Ship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.bship.games.util.Util.toIndex;
import static java.sql.Statement.RETURN_GENERATED_KEYS;

@Repository
public class ShipRepository {

    private final JdbcTemplate template;

    @Autowired
    public ShipRepository(JdbcTemplate template) {
        this.template = template;
    }

    public Ship create(Ship ship, Long boardId) {
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        template.update(con -> prepareInsertStatement(ship.copy().withBoardId(boardId).build(), con), holder);
        return ship.copy().withBoardId(boardId).withId(holder.getKey().longValue()).build();
    }

    private PreparedStatement prepareInsertStatement(Ship ship, Connection con) throws SQLException {
        PreparedStatement statement = con.prepareStatement("INSERT INTO ships(type, start, end, board_id) VALUE(?,?,?,?)",
                RETURN_GENERATED_KEYS);
        statement.setString(1, ship.getShipType().name());
        statement.setInt(2, toIndex(ship.getStart()));
        statement.setInt(3, toIndex(ship.getEnd()));
        statement.setLong(4, ship.getBoardId());
        return statement;
    }
}