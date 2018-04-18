package ru.andremoniy.sqbuilder;

import android.test.suitebuilder.annotation.SmallTest;

import org.junit.Assert;
import org.junit.Test;

import ru.andremoniy.sqlbuilder.SqlExpression;
import ru.andremoniy.sqlbuilder.SqlSelectStatement;

@SmallTest
public class SqlSelectStatementTest {
    /* Inner class that defines the table contents */
    public static abstract class LocationEntry {
        public static final String _ID = "id";
        public static final String TABLE_NAME = "location";
        public static final String COLUMN_NAME_NULLABLE = "NULLHACK";
        public static final String COLUMN_NAME_TIME = "time";
        public static final String COLUMN_NAME_ACCURACY = "accuracy";
        public static final String COLUMN_NAME_SPEED = "speed";
        public static final String COLUMN_NAME_BEARING = "bearing";
        public static final String COLUMN_NAME_ALTITUDE = "altitude";
        public static final String COLUMN_NAME_LATITUDE = "latitude";
        public static final String COLUMN_NAME_LONGITUDE = "longitude";
        public static final String COLUMN_NAME_RADIUS = "radius";
        public static final String COLUMN_NAME_HAS_ACCURACY = "has_accuracy";
        public static final String COLUMN_NAME_HAS_SPEED = "has_speed";
        public static final String COLUMN_NAME_HAS_BEARING = "has_bearing";
        public static final String COLUMN_NAME_HAS_ALTITUDE = "has_altitude";
        public static final String COLUMN_NAME_HAS_RADIUS = "has_radius";
        public static final String COLUMN_NAME_PROVIDER = "provider";
        public static final String COLUMN_NAME_LOCATION_PROVIDER = "service_provider";
        public static final String COLUMN_NAME_STATUS = "valid";
        public static final String COLUMN_NAME_BATCH_START_MILLIS = "batch_start";
        public static final String COLUMN_NAME_MOCK_FLAGS = "mock_flags";
    }

    @Test
    public void testBuildBasicQuery() {
        SqlSelectStatement sql = new SqlSelectStatement();
        sql.column("COLUMN");
        sql.from("MY_TABLE");
        sql.where("ANOTHER_COL", SqlExpression.SqlOperatorEqualTo, 2);
        sql.orderBy("TIME");

        Assert.assertEquals("SELECT [COLUMN] FROM [MY_TABLE] WHERE [ANOTHER_COL] = 2 ORDER BY [TIME] ASC;", sql.statement());
    }

    @Test
    public void testBuildBasicSubQuery() {
        SqlSelectStatement subsql = new SqlSelectStatement();
        subsql.column(new SqlExpression(String.format("MIN(%s)", LocationEntry._ID)), LocationEntry._ID);
        subsql.from(LocationEntry.TABLE_NAME);
        subsql.where(LocationEntry.COLUMN_NAME_STATUS, SqlExpression.SqlOperatorEqualTo, 2);
        subsql.orderBy(LocationEntry.COLUMN_NAME_TIME);
    }

    @Test
    public void testBuildSubQuery() {
        SqlSelectStatement subsql = new SqlSelectStatement();
        subsql.column(new SqlExpression(String.format("MIN(%s)", LocationEntry._ID)), LocationEntry._ID);
        subsql.from(LocationEntry.TABLE_NAME);
        subsql.where(LocationEntry.COLUMN_NAME_STATUS, SqlExpression.SqlOperatorEqualTo, 2);
        subsql.orderBy(LocationEntry.COLUMN_NAME_TIME);

        SqlSelectStatement sql = new SqlSelectStatement();
        sql.columns(new String[] {
            LocationEntry._ID,
            LocationEntry.COLUMN_NAME_PROVIDER,
            LocationEntry.COLUMN_NAME_TIME,
            LocationEntry.COLUMN_NAME_ACCURACY,
            LocationEntry.COLUMN_NAME_SPEED,
            LocationEntry.COLUMN_NAME_BEARING,
            LocationEntry.COLUMN_NAME_ALTITUDE,
            LocationEntry.COLUMN_NAME_RADIUS,
            LocationEntry.COLUMN_NAME_LATITUDE,
            LocationEntry.COLUMN_NAME_LONGITUDE,
            LocationEntry.COLUMN_NAME_HAS_ACCURACY,
            LocationEntry.COLUMN_NAME_HAS_SPEED,
            LocationEntry.COLUMN_NAME_HAS_BEARING,
            LocationEntry.COLUMN_NAME_HAS_ALTITUDE,
            LocationEntry.COLUMN_NAME_HAS_RADIUS,
            LocationEntry.COLUMN_NAME_LOCATION_PROVIDER,
            LocationEntry.COLUMN_NAME_STATUS,
            LocationEntry.COLUMN_NAME_BATCH_START_MILLIS,
            LocationEntry.COLUMN_NAME_MOCK_FLAGS
        });
        sql.from(LocationEntry.TABLE_NAME);
        sql.where(LocationEntry._ID, SqlExpression.SqlOperatorEqualTo, subsql);

        Assert.assertEquals("SELECT [id], [provider], [time], [accuracy], [speed], [bearing], [altitude], [radius], [latitude], [longitude], [has_accuracy], [has_speed], [has_bearing], [has_altitude], [has_radius], [service_provider], [valid], [batch_start], [mock_flags] FROM [location] WHERE [id] = (SELECT MIN(id) AS [id] FROM [location] WHERE [valid] = 2 ORDER BY [time] ASC);", sql.statement());
    }

    @Test
    public void testBuildQueryWithAgregateFunction() {
        SqlSelectStatement subsql = new SqlSelectStatement();
        subsql.column(new SqlExpression(String.format("MIN(%s)", LocationEntry._ID)), LocationEntry._ID);
        subsql.from(LocationEntry.TABLE_NAME);
        subsql.where(LocationEntry.COLUMN_NAME_STATUS, SqlExpression.SqlOperatorEqualTo, 2);
        subsql.orderBy(LocationEntry.COLUMN_NAME_TIME);

        Assert.assertEquals("SELECT MIN(id) AS [id] FROM [location] WHERE [valid] = 2 ORDER BY [time] ASC;", subsql.statement());
    }
}
