# Notes

Basic authentication is working with Google. It
requires you to have a google account and then forces
you to login. However, it's not doing the authentication
portion of the login. I need to explore adding that.

These rules need to be added to the server to block
unauth'd logins

{
  "rules": {
    ".read": "auth != null",
    ".write": "auth != null"
  }
}

This is the authentication section of the guide:
https://firebase.google.com/docs/auth/android/start/