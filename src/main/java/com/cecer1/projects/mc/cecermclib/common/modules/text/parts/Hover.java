package com.cecer1.projects.mc.cecermclib.common.modules.text.parts;

import com.cecer1.projects.mc.cecermclib.common.modules.text.parts.components.AbstractXmlTextComponent;

import java.util.List;

public class Hover {
    public static final Hover NONE = new Hover();

    public static class Text extends Hover {
        private final String json;
        public String getJson() {
            return this.json;
        }
        public Text(String json) {
            this.json = json;
        }
    }

    public static class Item extends Hover {
        private final String json;
        public String getJson() {
            return this.json;
        }
        public Item(String json) {
            this.json = json;
        }
    }

    public static class Entity extends Hover {
        private final String json;
        public String getJson() {
            return this.json;
        }
        public Entity(String json) {
            this.json = json;
        }
    }

    public static class Achievement extends Hover {
        private final String json;
        public String getJson() {
            return this.json;
        }
        public Achievement(String json) {
            this.json = json;
        }
    }

    public static class Segment extends Hover {
        private final List<AbstractXmlTextComponent> componentList;
        public List<AbstractXmlTextComponent> getComponentList() {
            return this.componentList;
        }
        public Segment(List<AbstractXmlTextComponent> componentList) {
            this.componentList = componentList;
        }
    }

    public static class Stat extends Hover {
        private final String json;
        public String getJson() {
            return this.json;
        }
        public Stat(String json) {
            this.json = json;
        }
    }
}
