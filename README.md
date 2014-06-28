# Nashorn Password Strength Check Example

This is the source code for the article
[Project Nashorn - JavaScript on the JVM](https://blog.codecentric.de/en/2014/06/project-nashorn-javascript-jvm-polyglott/).
The article is scheduled for release during my
[enterJS talk](http://www.enterjs.de/abstracts#javascript-und-java-kombinieren-polyglotte-programmierung-auf-jvm).

## Abstract

> Suppose you have a password strength checker in your user interface to
> help users choose secure and easily remembered passwords. Since
> estimating password strength involves more than just checking character
> classes you use a library like Dropbox's zxcvbn which avoids a few of the
> mistakes that ebay recently made. If you did this, then you are on the
> right track, but are you validating the password strength additionally
> on the server to reject weak passwords? It is common knowledge that one
> cannot trust user data and therefore needs to validate everything on the
> server. But how do you do this when the password strength check is
> written in JavaScript and the server is running a Java application?
