Proof-of-concert java reimplementation of fantastic sql [objective-c-sql-query-builder](https://github.com/ziminji/objective-c-sql-query-builder).


This is far from complete. Only very basic select statements are working. Putting here to allow others to help working on this. Also currently provided only as android-library as I'm primarly working in Android Studio, which seems not be supporting testing on non-android targets. However code is not using any Android specific packages so it should work in pure java world too.

Example:

```
SqlSelectStatement sql = new SqlSelectStatement();
sql.column("COLUMN");
sql.from("MY_TABLE");
sql.where("ANOTHER_COL", SqlExpression.SqlOperatorEqualTo, 2);
sql.orderBy("TIME");
String statement = sql.statement();
```

Some other example are in [SqlSelectStatementTest.java](lib/src/test/java/ru/andremoniy/sqbuilder/SqlSelectStatementTest.java)