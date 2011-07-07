package com.htsoft.test.message;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.htsoft.oa.dao.info.InMessageDao;
import com.htsoft.oa.dao.info.ShortMessageDao;
import com.htsoft.oa.model.info.InMessage;
import com.htsoft.test.BaseTestCase;
import java.io.PrintStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.junit.Test;

public class message extends BaseTestCase {

	@Resource
	private InMessageDao dao;

	@Resource
	private ShortMessageDao dao2;

	@Test
	public void set() {
		/* 44 */List list = this.dao.findByUser(new Long(1L));

		/* 46 */List<InMessage> listk = new ArrayList();

		/* 48 */for (int i = 0; i < list.size(); i++) {
			/* 49 */InMessage inMessage = (InMessage) ((Object[]) list.get(i))[0];

			/* 51 */listk.add(inMessage);
		}

		/* 54 */Gson gson = new Gson();
		/* 55 */Type type = new TypeToken<List<InMessage>>() {
		}
		/* 55 */.getType();
		/* 56 */System.out.println(gson.toJson(listk, type) + list.size());
	}
}
