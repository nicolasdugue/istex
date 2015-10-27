package util;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

/**
 * @author nicolas
 * 
 * TRACE: Entrée et sortie de méthodes <br />
DEBUG : Affichage de valeur de données <br />
INFO : Chargement d'un fichier de configuration, début et fin d'exécution d'un traitement long <br />
WARN : Erreur de login, données invalides <br />
ERROR : Toutes les exceptions capturées qui n'empêchent pas l'application de fonctionner <br />
FATAL : Indisponibilité d'une base de données, toutes les exceptions qui empêchent l'application de fonctionner <br />
 *
 */
public class SGLogger {
	static Logger logger;

	/**
	 * Get a log4j logger instance. Default level is warn.
	 * 
	 * @return log4j instance
	 */
	public static Logger getInstance() {
		if (logger == null) {
			StringBuilder motif = new StringBuilder();
			motif.append("[%p %d{HH:mm:ss}] %m in %l %n");

			PatternLayout layout = new PatternLayout(motif.toString());
			ConsoleAppender appender = new ConsoleAppender(layout);
			logger = Logger.getRootLogger();
			logger.addAppender(appender);
			logger.setLevel((Level) Level.WARN);
		}
		return logger;
	}

	public static void main(String[] args) {
		Logger log = SGLogger.getInstance();
		log.debug("msg de debogage");
		log.info("msg d'information");
		log.warn("msg d'avertissement");
		log.error("msg d'erreur");
		log.fatal("msg d'erreur fatale");
	}

}
