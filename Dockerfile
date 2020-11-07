#using tomcat latest
FROM tomcat
COPY dist/SWE645_RestBackend.war /usr/local/tomcat/webapps/