package tk.davinctor.app_with_datalayer.database.model;

/**
 * Created by Victor on 25.04.2016.
 */
public abstract class BaseEntity<TKey> {
    public abstract TKey getId();
}
