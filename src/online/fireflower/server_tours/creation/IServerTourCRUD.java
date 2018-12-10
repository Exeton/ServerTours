package online.fireflower.server_tours.creation;

//CRUD (Create, Read, Update, Delete)

import online.fireflower.server_tours.ServerTourInfo;

import java.util.List;

public interface IServerTourCRUD {

    void create(ServerTourInfo serverTourInfo);
    void delete(String name);
    void update(ServerTourInfo serverTourInfo);

    ServerTourInfo readServerTourInfo(String name);
    List<ServerTourInfo> readAllServerTourInfos();

}
