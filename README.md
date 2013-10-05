Play StaticWebsiteTemplate
==========================

This is the standard Play template (play 2.2.0) bundled with a helper class (playpackager.Packager) to write out static versions of the site.

Usage
=====

Since this is a standard Play 2.x application, feel free to use all the built-in play console commands.

To package the site, use ```run-main playpackager.Packager```. This will create a timestamp directory inside ```output```, with a copy of everything inside ```public```, and all the static HTML files specified by ```Application.mapping``` and ```Application.extraMaps```.
