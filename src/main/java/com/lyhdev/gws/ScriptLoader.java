package com.lyhdev.gws;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class ScriptLoader
{
	public ScriptLoader() {
	}

	public static void main(String args[])
	{
		System.setSecurityManager(new UnsafeSecurityManager());

		ScriptLoader loader = new ScriptLoader();
		
		try {
			if ("groovy".equals(System.getProperty("core.script.type"))) {
				loader.loadGroovy(System.getProperty("core.script.url"));
			}
			else {
				loader.load(
					System.getProperty("core.script.type"),
					System.getProperty("core.script.url")
				);
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		
		//System.exit(0);
	}

	public void load(String scriptType, String scriptURL) throws Exception {
		URL url = new URL(scriptURL);
		URLConnection conn = url.openConnection();
		BufferedReader reader = new BufferedReader(
			new InputStreamReader(conn.getInputStream(), "UTF-8")
		);

		ScriptEngineManager factory = new ScriptEngineManager();
		ScriptEngine engine = factory.getEngineByName(scriptType);
		engine.put("loader", this);
		engine.eval(reader);
		reader.close();
	}

	private GroovyShell shell = null;

	public void loadGroovy(String scriptURL) throws Exception {
		System.out.println("Load Groovy " + scriptURL);

		URL url = new URL(scriptURL);
		URLConnection conn = url.openConnection();
		BufferedReader reader = new BufferedReader(
			new InputStreamReader(conn.getInputStream(), "UTF-8")
		);

		if (shell==null) {
			Binding binding = new Binding();
			binding.setVariable("loader", this);
			shell = new GroovyShell(binding);
		}
		shell.evaluate(reader);
		reader.close();
	}
}
