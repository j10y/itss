package net.shopin;

import javax.management.*;
import java.lang.management.ManagementFactory;

public interface ConstantsMBean {
    public boolean isDEBUG();

    public void setDEBUG(boolean DEBUG);
}