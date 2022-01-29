package com.cecer1.projects.mc.cecermclib.common.modules.text.parts;

public class Click {
    public static final Click NONE = new Click();

    public static class OpenFile extends Click {
        private final String path;
        public String getPath() {
            return this.path;
        }
        public OpenFile(String path) {
            this.path = path;
        }
    }

    public static class RunCommand extends Click {
        private final String command;
        public String getCommand() {
            return this.command;
        }
        public RunCommand(String command) {
            this.command = command;
        }
    }

    public static class Suggest extends Click {
        private final String suggest;
        public String getSuggest() {
            return this.suggest;
        }
        public Suggest(String suggest) {
            this.suggest = suggest;
        }
    }

    public static class ChangePage extends Click {
        private final String page;
        public String getPage() {
            return this.page;
        }
        public ChangePage(String page) {
            this.page = page;
        }
    }

    public static class Url extends Click {
        private final String url;
        public String getUrl() {
            return this.url;
        }
        public Url(String url) {
            this.url = url;
        }
    }
}
