package model;

import java.io.IOException;

public interface Loadable {
    public void load() throws IOException, ClassNotFoundException;
}
