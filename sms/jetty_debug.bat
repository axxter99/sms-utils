copy impl\target\test-classes\org tool\target\sms_ui\WEB-INF\classes
cd tool
copy test-resources\web.xml target\sms_ui\WEB-INF\web.xml /y
copy test-resources\components.xml target\sms_ui\WEB-INF\components.xml /y
mvnDebug -o -Dmaven.test.skip=true jetty:run-war