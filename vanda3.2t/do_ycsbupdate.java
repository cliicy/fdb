public final int update(final String table, final String key, final HashMap<String, ByteIterator> values) {
// 对原来的update函数做修改，在每次update时都多做几次操作<br>    
int ret = updateOneTable(table, key, values);
if (ret != 0) {
    return ret;
}
for (int i = 0; i < TABLE_NUM; ++i) {
String tableName = table + String.valueOf(i);
ret = updateOneTable(tableName, key, values);
if (ret != 0) {
    return ret;
}
} 
return 0; 
}

public final int updateOneTable(final String table, final String key, final HashMap<String, ByteIterator> values) {
try { 
 final MongoCollection collection = database.getCollection(table);
final DocumentBuilder query = BuilderFactory.start().add("_id", key);
final DocumentBuilder update = BuilderFactory.start();
final DocumentBuilder fieldsToSet = update.push("$set");
for (final Map.Entry<String, ByteIterator> entry : values.entrySet()) { 
fieldsToSet.add(entry.getKey(), entry.getValue().toArray()); 
}
final long res = collection.update(query, update, false, false, writeConcern);
return res == 1 ? 0 : 1; <br>    } catch (final Exception e) {<br>        System.err.println(e.toString()); return 1; <br>    } <br>}


