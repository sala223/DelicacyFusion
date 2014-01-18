package com.df.masterdata.test;

import javax.inject.Inject;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import com.df.masterdata.dao.DiningTableDao;
import com.df.masterdata.dao.RoomDao;
import com.df.masterdata.entity.DiningTable;
import com.df.masterdata.entity.Room;
import com.df.masterdata.entity.StoreObjectId;

@Transactional
public class RoomDaoTest extends MasterDataJPABaseTest {

	@Inject
	private RoomDao roomDao;

	@Inject
	private DiningTableDao tableDao;

	@Before
	public void sampleRooms() {
		Room room = new Room();
		room.setStoreCode("S1");
		room.setCode("ROOM1");
		room.setName("A001");
		DiningTable table1 = new DiningTable();
		table1.setBarCode("TV10001");
		table1.setCapacity(10);
		table1.setCode("S1000001");
		table1.setStoreCode("S1");
		DiningTable table2 = new DiningTable();
		table2.setBarCode("TV10002");
		table2.setCapacity(10);
		table2.setCode("S1000002");
		table2.setStoreCode("S1");
		tableDao.addDiningTable(table1);
		tableDao.addDiningTable(table2);
		room.addDiningTable(table1);
		room.addDiningTable(table2);
		roomDao.insert(room);
		room = new Room();
		room.setStoreCode("S1");
		room.setCode("ROOM2");
		room.setName("A002");
		roomDao.insert(room);
		room = new Room();
		room.setStoreCode("S1");
		room.setCode("ROOM3");
		room.setName("A003");
		roomDao.insert(room);
		room = new Room();
		room.setStoreCode("S1");
		room.setCode("ROOM4");
		room.setName("A004");
		roomDao.insert(room);
		roomDao.getEntityManager().flush();
	}

	@Test
	public void testFindRoomByCode() {
		Room froom = roomDao.findRoomByRoomCode("S2", "ROOM3");
		TestCase.assertNull(froom);
		froom = roomDao.findRoomByRoomCode("S1", "ROOM1");
		TestCase.assertNotNull(froom);
	}

	@Test
	public void testFindRoomByStoreObjectId() {
		Room froom = roomDao.find(Room.class, new StoreObjectId("S2", "ROOM3"));
		TestCase.assertNull(froom);
		froom = roomDao.find(Room.class, new StoreObjectId("S1", "ROOM3"));
		TestCase.assertNotNull(froom);
	}

	@Test
	public void testFindRoomByName() {
		Room froom = roomDao.findRoomByRoomName("S2", "A003");
		TestCase.assertNull(froom);
		froom = roomDao.findRoomByRoomName("S1", "A001");
		TestCase.assertNotNull(froom);
	}

	@Test
	public void getGetRoomList() {
		TestCase.assertEquals(roomDao.getRooms("S1").size(), 4);
	}
}
