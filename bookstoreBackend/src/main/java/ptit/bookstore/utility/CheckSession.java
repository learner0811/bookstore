package ptit.bookstore.utility;

import javax.servlet.http.HttpSession;

public class CheckSession {
	public static boolean checkUserSession(HttpSession session) {
		return session.getAttribute("user") != null;
	}
}
