package de.codecentric;

import org.apache.commons.io.IOUtils;

import javax.script.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class StrengthChecker {

  /**
   * This path is defined by the the WebJars project.
   * Also see: http://www.webjars.org/
   */
  private static final String ZXCVBN_PATH = "/META-INF/resources/webjars/zxcvbn/1.0/zxcvbn.js";

  private final ScriptEngine engine;
  private final Bindings engineScope;

  public StrengthChecker() {
    ScriptEngineManager manager = new ScriptEngineManager();
    engine = manager.getEngineByName("nashorn");

    // register global scope as 'window'
    engineScope = engine.getBindings(ScriptContext.ENGINE_SCOPE);
    engineScope.put("window", engineScope);

    try {
      engine.eval(getResourceContents(ZXCVBN_PATH));
    } catch (ScriptException e) {
      throw new RuntimeException(e);
    }
  }

  public Strength check(String pw) {
    // simply plucking the password into the engine.eval(...) call through
    // string concatenation would be very dangerous (string injection).
    // We don't even want to mess with proper string escaping as it is
    // hard to get right. Instead we manipulate the engine's scope to
    // safely add a new global variable.
    SimpleScriptContext scriptContext = new SimpleScriptContext();
    Bindings globalScope = engine.createBindings();

    scriptContext.setBindings(globalScope, ScriptContext.GLOBAL_SCOPE);
    scriptContext.setBindings(engine.getBindings(ScriptContext.ENGINE_SCOPE), ScriptContext.ENGINE_SCOPE);

    globalScope.put("pw", pw);

    try {
      Map<String, Object> result;
      result = (Map<String, Object>) engine.eval("zxcvbn(pw);", scriptContext);

      return new Strength(
        ((Double) result.get("entropy")).intValue(),
        (int) result.get("score"),
        ((Double) result.get("crack_time")).intValue()
      );
    } catch (ScriptException e) {
      throw new RuntimeException(e);
    }
  }

  private String getResourceContents(String path) {
    InputStream in = this.getClass().getResourceAsStream(path);
    if (in == null) {
      throw new RuntimeException("Resource '" + path + "' cannot be found.");
    }

    try {
      return IOUtils.toString(in, StandardCharsets.UTF_8);
    } catch (IOException e) {
      throw new RuntimeException(e);
    } finally {
      IOUtils.closeQuietly(in);
    }
  }
}
