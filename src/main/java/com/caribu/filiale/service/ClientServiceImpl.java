package com.caribu.filiale.service;

import io.vertx.core.Future;
import org.hibernate.reactive.stage.Stage;

import com.caribu.filiale.data.ClientEntityMapper;
import com.caribu.filiale.data.ClientDTOMapper;
import com.caribu.filiale.model.Client;
import com.caribu.filiale.model.ClientDTO;

import javax.persistence.criteria.*;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

public class ClientServiceImpl implements ClientService {

  private Stage.SessionFactory sessionFactory;

  public ClientServiceImpl(Stage.SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  public Future<ClientDTO> addClient(ClientDTO client) {
    ClientEntityMapper entityMapper = new ClientEntityMapper();
    Client entity = entityMapper.apply(client);
    CompletionStage<Void> result = sessionFactory.withTransaction((s, t) -> s.persist(entity));
    ClientDTOMapper dtoMapper = new ClientDTOMapper();
    Future<ClientDTO> future = Future.fromCompletionStage(result).map(v -> dtoMapper.apply(entity));
    return future;
  }

  @Override
  public Future<Optional<ClientDTO>> getClientBycompanyName(String companyName) {
    ClientDTOMapper dtoMapper = new ClientDTOMapper();
    CompletionStage<Client> result = sessionFactory.withTransaction((s, t) -> s.find(Client.class, companyName));
    Future<Optional<ClientDTO>> future = Future.fromCompletionStage(result)
        .map(r -> Optional.ofNullable(r))
        .map(r -> r.map(dtoMapper));
    return future;
  }

  @Override
  public Future<Optional<ClientDTO>> findClientByCompany(String companyName) {
      ClientDTOMapper dtoMapper = new ClientDTOMapper();
      CriteriaBuilder criteriaBuilder = sessionFactory.getCriteriaBuilder();
      CriteriaQuery<Client> criteriaQuery = criteriaBuilder.createQuery(Client.class);
      Root<Client> root = criteriaQuery.from(Client.class);
      Predicate predicate = criteriaBuilder.equal(root.get("companyName"), companyName);
      criteriaQuery.where(predicate);

      CompletionStage<Client> result = sessionFactory.withTransaction((s,t)
      -> s.createQuery(criteriaQuery).getSingleResult());
      System.out.println("ccc" + result);
      Future<Optional<ClientDTO>> future = Future.fromCompletionStage(result)
            .map(r -> Optional.ofNullable(r))
          .map(r -> r.map(dtoMapper));
      return future;
  }
  //primo tentativo - edo
  @Override
  public Future<ClientDTO> addClientIfNotExistsByName(ClientDTO client) {
    System.out.println("addClientIfNotExistsByName");
    return getClientBycompanyName(client.getCompanyName())
      .flatMap(clientOpt -> clientOpt
        .map(Future::succeededFuture)
        .orElseGet(() -> {
          System.out.println("Here is the client" + client.toString());
          return addClient(client);}));
  }
}
