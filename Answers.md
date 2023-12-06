# Project Overview

The Appointment Booking System manages user appointments using the concepts of appointments, availability slots, notifications, and reminders.

## Appointment Logic

### Entities

- **Appointment:** Represents a booked appointment between a booker user and an available slot.
  - Attributes: ID, availability slot, booker user, notifications, start time, end time, appointment status.

### Functionality

1. **Booking Appointments:** Users can book available slots, creating a new appointment.
2. **Canceling Appointments:** Users can cancel booked appointments, changing the appointment status.

## AvailabilitySlot Logic

### Entities

- **AvailabilitySlot:** Represents a time slot during which a user is available for appointments.
  - Attributes: ID, user, start time, end time, available.

### Functionality

1. **Adding Availability Slots:** Users can add time slots during which they are available.
2. **Removing Availability Slots:** Users can remove existing availability slots.

## Notification Logic

### Entities

- **Notification:** Represents a notification associated with a specific appointment.
  - Attributes: ID, notification type, recipient email, recipient ID, subject, message, sent flag, sent time, booked appointment.

### Notification Types

1. **NEW_BOOKING:** Notification for a new booking.
2. **CANCEL:** Notification for appointment cancellation.
3. **REMINDER:** Notification to remind participants of an upcoming appointment.

### Functionality

1. **Creating Notifications:** Notifications are created for specific events like new bookings, cancellations, and reminders.
2. **Sending Notifications:** Notifications can be sent to recipients, updating the sent flag and sent time.

## Reminder Logic

### Functionality

1. **Upcoming Appointments:** A scheduled task checks for upcoming appointments (15 minutes in the future).
2. **Creating Reminder Notifications:** For each upcoming appointment, two reminder notifications are created and associated with the appointment.
3. **Sending Reminder Notifications:** Initially, logs are printed for testing. Later, email functionality can be integrated.


## Sequence (Booking an Appointment)

1. **User initiates booking process:**
   - User requests to book an appointment.
2. **System checks availability:**
   - System list available slots. 
3. **User selects a slot:**
   - User chooses an available slot.
4. **System creates an appointment:**
   - System generates a new appointment with passed slotId and bookerUserId.
5. **Notification created:**
   - System generates a "NEW_BOOKING" notification. **(PART E)**
6. **Notification sent:**
   - Notification is sent to the booker and provider users. **(PART E)**

## Sequence  (Canceling an Appointment)

1. **User initiates cancellation:**
   - User requests to cancel an appointment.
2. **System updates appointment status:**
   - System changes the appointment status to "CANCELLED."
3. **Notification created:**
   - System generates a "CANCEL" notification. **(PART E)**
4. **Notification sent:**
   - Notification is sent to the booker and provider user. **(PART E)**

## Sequence (Reminder Logic) (PART D)

1. **Reminder Service triggers:**
   - The scheduled task in the Reminder Service runs.
2. **Upcoming Appointments checked:**
   - The system identifies appointments starting in 15 minutes.
3. **Reminder Notifications created:**
   - System generates "REMINDER" notifications for each participant.
4. **Notifications associated with the Appointment:**
   - Notifications are linked to the respective appointment.
5. **Notifications sent (logging for testing):**
   - Log entries are made indicating the reminder notifications.(I just logged the emails, in future real-time email service can be implemented)



