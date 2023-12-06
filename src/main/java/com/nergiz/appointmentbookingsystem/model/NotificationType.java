package com.nergiz.appointmentbookingsystem.model;

public enum NotificationType {
    NEW_BOOKING("New Booking", "You have a new appointment booking with appointment ID: "),
    CANCEL("Cancellation", "Your appointment has been cancelled with appointment ID: "),
    REMINDER("Reminder", "Upcoming meeting with appointment details with appointment ID: ");

    private final String displayName;
    private final String defaultSubject;

    NotificationType(String displayName, String defaultSubject) {
        this.displayName = displayName;
        this.defaultSubject = defaultSubject;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDefaultSubject() {
        return defaultSubject;
    }
}
