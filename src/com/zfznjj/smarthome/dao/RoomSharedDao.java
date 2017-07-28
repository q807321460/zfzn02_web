package com.zfznjj.smarthome.dao;

import com.zfznjj.smarthome.model.RoomShared;

public interface RoomSharedDao {
	int saveOrUpdate(RoomShared roomShared);
	int delete(int roomId, int userId);
	RoomShared select(int roomId, int userId);
}
