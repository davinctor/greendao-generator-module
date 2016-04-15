package tk.davinctor;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class GreenDaoGenerator {
    private static final int VERSION = 1;
    private static final String PACKAGE_DESTINATION = "tk.davinctor.app_with_datalayer.database";
    private static final String DESTINATION_FOLDER = "./app/src/main/java";

    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(VERSION, PACKAGE_DESTINATION);

        Entity userEntity = createUserEntity(schema);

        new DaoGenerator().generateAll(schema, DESTINATION_FOLDER);
    }

    private static Entity createUserEntity(Schema schema) {
        Entity userEntity = schema.addEntity("user");

        userEntity.addIdProperty().primaryKey().autoincrement();
        userEntity.addStringProperty("name");

        return userEntity;
    }
}
