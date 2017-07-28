package com.zfznjj.smarthome.dao;

import java.util.List;


import com.zfznjj.smarthome.model.UserRoom;

public interface UserRoomDao {
	int saveOrUpdate(UserRoom userRoom);
	int delete(String masterCode, int roomIndex);
	UserRoom select(String masterCode, int roomIndex);
	int updateRoomSequ(String masterCode, int roomSequ);
	List<UserRoom> select(String masterCode);
}
