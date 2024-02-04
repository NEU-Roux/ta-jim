package edu.northeastern.parceldemo;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

/**
 * Here the Class User implements the Parcelable interface,
 * which will allow User objects to be passed between activities or components.
 * <p>
 * Q: What is a Parcelable?
 * A: It's an interface in the Android SDK that represents a contract for serializing and deserializing objects.
 *    The design is intended to be more efficient than vanilla Java's standard serialization.
 *    Overall it is the preferred way to pass complex data between activities, services, and processes in android apps.
 * <p>
 * Here's a great stack overflow discussion talking about Serializable vs Parcelable in more depth:
 * https://stackoverflow.com/questions/3323074/android-difference-between-parcelable-and-serializable
 */
public class User implements Parcelable {

    private String name;
    private int age;
    private String email;

    // Standard constructor for User object.
    public User(String name, int age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
    }

    // Getters and Setters.
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    /**
     * Private constructor that takes a Parcel and reads the data in the same order it was written.
     * This is used for creating a User instance from a Parcel.
     *
     * @param in - The Parcel containing the User object data.
     */
    private User(Parcel in) {
        name = in.readString();
        age = in.readInt();
        email = in.readString();
    }

    /**
     * The CREATOR field is a static instance of the Parcelable.Creator interface,
     * allowing the reconstruction of User instances from Parcel data.
     * This is essential for passing User objects between Android components.
     */
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {

        /**
         * Creates a User instance from a Parcel.
         * This method is called by the Android system when deserializing a User object from a Parcel.
         * It reads the state of the User object from the Parcel
         * that was previously written by the writeToParcel method.
         */
        public User createFromParcel(Parcel in) {
            return new User(in); // Notice we are using our private constructor here.
        }

        /**
         * This method creates a new array of the User class.
         * It's used by the Android system when your app passes an array of User objects.
         * Sometimes it might be necessary to pass more than one user object between components or activities.
         *
         * @param size Size of the array.
         * @return An array of User objects, with each element initialized to null.
         */
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    /**
     * Describe the kinds of special objects contained in this Parcelable's representation.
     * Typically returns 0 unless dealing with a specific child of Parcelable that's also
     * a FileDescriptor or a Binder.
     * <p>
     * Q: What is a FileDescriptor?
     * A: https://developer.android.com/reference/java/io/FileDescriptor
     * Q: What is a Binder?
     * A: https://developer.android.com/reference/android/os/Binder
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Assembles the object into a Parcel.
     * Writes the User properties to the destination parcel dest in a specific order.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written (e.g., Parcelable.WRITE_RETURN_VALUE).
     */
    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(age);
        dest.writeString(email);
    }
}
