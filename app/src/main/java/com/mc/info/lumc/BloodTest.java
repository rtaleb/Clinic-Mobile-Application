package com.mc.info.lumc;

import java.io.Serializable;
import java.util.HashMap;

public abstract class BloodTest extends Examination implements Serializable {
    public BloodTest() {}

    public abstract HashMap<String, String> toHashMap();
}
