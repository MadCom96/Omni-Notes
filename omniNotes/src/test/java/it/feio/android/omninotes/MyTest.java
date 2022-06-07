package it.feio.android.omninotes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static java.lang.Thread.sleep;
import static it.feio.android.omninotes.utils.ConstantsBase.DATE_FORMAT_SORTABLE;

import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import it.feio.android.omninotes.helpers.date.DateHelper;

public class MyTest {
    DateHelper dateHelper;
    @Before
    public void setUp(){}
    @Test
    public void getSortableDateTest() throws ParseException, InterruptedException {
        class SDPair{
            String s;
            Date d;
            SDPair(String s, Date d){
                this.s = s;
                this.d = d;
            }
            public Date getD() { return d; }
            public String getS() { return s; }
        }

        List<SDPair> stringDatas = new LinkedList<> ();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_SORTABLE);
        Random random = new Random();

        for (int i = 0; i < 10; i++){
            int time = random.nextInt(1000);
            sleep(time);
            String data = dateHelper.getSortableDate();
            stringDatas.add(new SDPair(data, sdf.parse(data)));
        }//이미 정렬되어 나올 것
        //역순정렬
        Collections.sort(stringDatas, (a, b) -> b.getS().compareTo(a.getS()));
        for (int i = 1; i < 9; i++){
            //역순으로 잘 정렬이 되었는가
            assertTrue(stringDatas.get(i).getD().compareTo(stringDatas.get(i - 1).getD()) < 1);
        }
    }

    @Test
    public void onDateSetTest(){
        String formats[] = {"dd MM YYYY",
                            "EEEE, MMM d, ''yy",
                            "yyyyy.MMM.dd"};
        assertEquals("04 07 2001", dateHelper.onDateSet(2001, 7 - 1,4, formats[0]));
        assertEquals("일요일, 10월 28, '12", dateHelper.onDateSet(2812, 10 - 1, 28, formats[1]));
        assertEquals("01289.3월.23", dateHelper.onDateSet(1289, 3 - 1, 23, formats[2]));
    }

}
