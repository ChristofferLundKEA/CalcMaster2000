package com.alpha.solutions.calcmaster2000.enums;

public enum Status {
        NOT_STARTED("Not Started"),
        READY("Ready"),
        IN_PROGRESS("In Progress"),
        IN_REVIEW("In Review"),
        DONE("Done");

        private final String displayName;

        Status(String displayName){
                this.displayName = displayName;
        }

        public String getDisplayName(){
                return displayName;
        }
}
