/*
 * FXGL - JavaFX Game Library. The MIT License (MIT).
 * Copyright (c) AlmasB (almaslvl@gmail.com).
 * See LICENSE for details.
 */

package com.almasb.fxgl.entity.diff;

import com.almasb.fxgl.entity.EntityTest;
import com.almasb.fxgl.entity.component.Component;

/**
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
public class InjectableComponent extends Component {

    private EntityTest.CustomDataComponent component;

    @Override
    public void onAdded() {
        if (component == null || !"Inject".equals(component.getData())) {
            throw new RuntimeException("Injection failed!");
        }
    }
}
