import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SentenceAnalyserTest {
    private static Logger logger = Logger.getLogger(SentenceAnalyserTest.class);
    private SentenceAnalyser SentenceAnalyser;

    @BeforeClass
    public static void beforeTests(){
        logger.info("@BeforeClass");
    }
    @Before
    public void before(){
        logger.info("@Before");
        this.SentenceAnalyser= new SentenceAnalyser();
    }

    @Test
    public void doSomeTestPositive(){
        logger.info("doSomeTest");
        String res = null;
        try {
            //res = this.SentenceAnalyser.sentenceWordfinder();
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals("",res);
    }
}
