<?php
header('Content-type: application/x-java-jnlp-file');
//header('Content-type: text/plain');
header('Content-disposition: inline; filename=webstart.jnlp');
echo "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
?>
<jnlp spec="1.0+" codebase="http://gws.lyhdev.com/">
	<information>
		<title>Loader</title>
		<vendor>lyhcode</vendor>
		<homepage href="http://gws.lyhdev.com/" />
		<description>Groovy Web Start</description>
	</information>
	<security>
		<all-permissions/>
	</security>
	<resources>
		<j2se version="1.6+" href="http://java.sun.com/products/autodl/j2se" />
		<jar href="gws-0.0.1.jar" />
		<property name="core.script.type" value="groovy" />
		<property name="core.script.url" value="<?php echo $_REQUEST['script_url']?>" />
	</resources>
	<application-desc main-class="com.lyhdev.gws.ScriptLoader" />
</jnlp>
