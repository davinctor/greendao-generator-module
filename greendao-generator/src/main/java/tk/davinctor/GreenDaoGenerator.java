package tk.davinctor;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Index;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;

public class GreenDaoGenerator {
    private static final int VERSION = 1;
    private static final String PACKAGE_DESTINATION = "tk.davinctor.app_with_datalayer.database";
    private static final String ENTITIES_PACKAGE = PACKAGE_DESTINATION + ".model";
    private static final String DAOS_PACKAGE = PACKAGE_DESTINATION + ".dao";
    private static final String TESTS_PACKAGE = PACKAGE_DESTINATION + ".test";
    private static final String DESTINATION_FOLDER = "./app/src/main/java";

    public static class CustomSchema extends Schema {
        public CustomSchema(int version, String defaultJavaPackage) {
            super(version, defaultJavaPackage);
        }

        @Override
        public Entity addEntity(String className) {
            Entity entity = super.addEntity(className);

            entity.addImport("de.greenrobot.dao.AbstractDao");

            return entity;
        }
    }

    public static void main(String[] args) throws Exception {
        Schema schema = new CustomSchema(VERSION, PACKAGE_DESTINATION);

        Entity userEntity = createUserEntity(schema);

        new DaoGenerator().generateAll(schema, DESTINATION_FOLDER);
    }

    private static Entity createUserEntity(Schema schema) {
        Entity userEntity = schema.addEntity("User");

        // move to schema addEntity() method
        userEntity.setJavaPackage(ENTITIES_PACKAGE);
        userEntity.setJavaPackageDao(DAOS_PACKAGE);
        userEntity.setJavaPackageTest(TESTS_PACKAGE);

        userEntity.setJavaDoc("Entity for string user information");

        userEntity.addIdProperty().index();
        // equals to
        // userEntity.addLongProperty("ud").primaryKey();
        // also can be string id
        // userEntity.addStringProperty("uuid").primaryKey().primaryKeyDesc();

        // string
        userEntity.addStringProperty("name");
        // integral
        Property serverId = userEntity.addLongProperty("serverId").notNull().unique().getProperty();
        Index index = new Index();
        index.addProperty(serverId);
        userEntity.addIndex(index);

        userEntity.addIntProperty("likeCount");
        userEntity.addShortProperty("shortTypeColumn");
        userEntity.addByteProperty("bytePropertyColumn");
        // real
        userEntity.addFloatProperty("salaryFloat");
        userEntity.addDoubleProperty("salaryDouble");
        // boolean
        userEntity.addBooleanProperty("isAdmin");
        // timestamp
        userEntity.addDateProperty("birthday")
                .codeBeforeField("private static final Date DEFAULT_BIRTHDAY = new Date();");
        // blob
        userEntity.addByteArrayProperty("userPhoto");

        userEntity.addStringProperty("javaDocSample")
                .javaDocField("Documentation for field")
                .javaDocGetterAndSetter("Documentation for setter and getter methods")
                .javaDocGetter("Documentation for getter method")
                .javaDocSetter("Documentation for setter method");

        userEntity.addImport("java.util.Date");
        userEntity.addImport("tk.davinctor.app_with_datalayer.database.model.BaseEntity");
        userEntity.addImport("tk.davinctor.app_with_datalayer.database.model.Storable");
        userEntity.addImport("android.os.Parcelable");
        // greenDao have special method for serializable - but not adding import for Serializable interface
        userEntity.addImport("java.io.Serializable");

        userEntity.setSuperclass("BaseEntity<Long>");
        userEntity.implementsInterface("Storable", "Parcelable");
        userEntity.implementsSerializable();

        // ContentProvider
        userEntity.addContentProvider();

        userEntity.setHasKeepSections(true);

        // ??????????????????????????????????????
        userEntity.getCodeBeforeClass();
        userEntity.setCodeBeforeClass();
        userEntity.getAdditionalImportsDao().add();
        userEntity.validatePropertyExists();
        userEntity.setSkipGenerationTest(false);

        return userEntity;
    }
}
