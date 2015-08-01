package tk.playerforcehd.chat.start;

import java.text.DateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class LogFormat extends Formatter {

	protected final Date date = new Date();
	protected final DateFormat dateformat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM);
	
	@Override
	public String format(LogRecord record) {
		date.setTime(record.getMillis());
		StringBuffer sb = new StringBuffer();
		sb.append("["+dateformat.format(date)+"]");
		sb.append(" [");
		sb.append(record.getLevel());
		sb.append("] ");
		sb.append(record.getMessage());
		sb.append("\n");
		return sb.toString();
	}
}
