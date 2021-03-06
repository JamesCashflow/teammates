package teammates.ui.webapi.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;

import teammates.common.util.Config;
import teammates.common.util.JsonUtils;
import teammates.ui.webapi.output.ApiOutput;
import teammates.ui.webapi.output.MessageOutput;

/**
 * Action result in form of JSON object.
 *
 * <p>This is the most common format for REST-ful back-end API response.
 */
public class JsonResult extends ActionResult {

    private final ApiOutput output;

    public JsonResult(ApiOutput output) {
        super(HttpStatus.SC_OK);
        this.output = output;
    }

    public JsonResult(String message) {
        this(message, HttpStatus.SC_OK);
    }

    public JsonResult(String message, int statusCode) {
        super(statusCode);
        this.output = new MessageOutput(message);
    }

    public ApiOutput getOutput() {
        return output;
    }

    @Override
    public void send(HttpServletResponse resp) throws IOException {
        output.setRequestId(Config.getRequestId());
        resp.setStatus(getStatusCode());
        resp.setContentType("application/json");
        PrintWriter pw = resp.getWriter();
        pw.print(JsonUtils.toJson(output));
    }

}
