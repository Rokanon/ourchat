package app.core.beans;

import java.util.List;
import app.core.domain.Model;

/**
 *
 * @author dark
 * @param <Dto>
 */
public abstract class AbstractList<Dto extends Model> {

    public abstract List<Dto> getList();

}
