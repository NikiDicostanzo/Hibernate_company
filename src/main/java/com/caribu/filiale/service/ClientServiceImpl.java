package com.caribu.filiale.service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.reactive.stage.Stage;

import com.caribu.filiale.data.ClientDTOMapper;
import com.caribu.filiale.data.ClientEntityMapper;
import com.caribu.filiale.model.Client;
import com.caribu.filiale.model.ClientDTO;

import io.vertx.core.Future;

public class ClientServiceImpl implements ClientService {

  private Stage.SessionFactory sessionFactory;

  public ClientServiceImpl(Stage.SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  public Future<ClientDTO> addClient(ClientDTO client) {
    System.out.println("New client");
    ClientEntityMapper entityMapper = new ClientEntityMapper();
    Client entity = entityMapper.apply(client);
    CompletionStage<Void> result = sessionFactory.withTransaction((s, t) -> s.persist(entity));
    ClientDTOMapper dtoMapper = new ClientDTOMapper();
    Future<ClientDTO> future = Future.fromCompletionStage(result).map(v -> dtoMapper.apply(entity));
    return future;
  }

  @Override
  public Future<Optional<ClientDTO>> getClientById(Integer id) {
    ClientDTOMapper dtoMapper = new ClientDTOMapper();
    CompletionStage<Client> result = sessionFactory.withTransaction((s, t) -> s.find(Client.class, id));
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

      // Se il cliente non esiste GetSingleResult() lancerà una NoResultException -> eccezione non rilevata dal metodo orElseGet{} => getResultList
      CompletionStage<List<Client>> result = sessionFactory.withTransaction((s,t)
      -> s.createQuery(criteriaQuery).getResultList());  
      Future<Optional<ClientDTO>> future = Future.fromCompletionStage(result)
      .map(r -> r.stream().findFirst())
      .map(r -> r.map(dtoMapper));      
      return future;
  }


  @Override
  public Future<ClientDTO> addClientIfNotExistsByName(String companyName) {
    return findClientByCompany(companyName)//client.getCompanyName())
           .flatMap(clientOpt -> clientOpt
                      .map(Future::succeededFuture)
                      .orElseGet(() -> {
                          ClientDTO client = new ClientDTO(null, 1111, companyName); 
                          return addClient(client);}));
  }
}
