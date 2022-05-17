package com.game.service;

import com.game.controller.PlayerOrder;
import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.repository.PlayerRepositoryV2;
//import org.hibernate.Session; //Оставлю как память :)
//import org.hibernate.SessionFactory;
//import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Date;
import java.util.*;

@Service
public class PlayerService {
    //Работает с конкретной логикой


    @Autowired
    private PlayerRepositoryV2 playerRepository;

    @Autowired
    EntityManagerFactory entityManagerFactory;

    public List<Player> getAll(String name, String title, Race race, Profession profession, Long before, Long after,
                               Boolean banned, Integer minExperience, Integer maxExperience,
                               Integer minLevel, Integer maxLevel) {


        EntityManager manager = entityManagerFactory.createEntityManager();
        manager.getTransaction().begin();
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<Player> query = cb.createQuery(Player.class);
        Root<Player> from = query.from(Player.class);

        List<Predicate> predicateList = new ArrayList<>();
        Predicate[] predicates = new Predicate[1];
        if (name != null) {
            predicates[0] = cb.like(from.get("name"), "%" + name + "%");
            predicateList.add(predicates[0]);
        }
        if (title != null) {
            predicates[0] = cb.like(from.get("title"), "%" + title + "%");
            predicateList.add(predicates[0]);
        }
        if (race != null) {
            predicates[0] = cb.equal(from.get("race"), race);
            predicateList.add(predicates[0]);
        }
        if (profession != null) {
            predicates[0] = cb.equal(from.get("profession"), profession);
            predicateList.add(predicates[0]);
        }
        if (before != null) {
            predicates[0] = cb.greaterThanOrEqualTo(from.get("birthday"), new Date(before));
            predicateList.add(predicates[0]);
        }
        if (after != null) {
            predicates[0] = cb.lessThanOrEqualTo(from.get("birthday"), new Date(after));
            predicateList.add(predicates[0]);
        }
        if (banned != null) {
            predicates[0] = cb.equal(from.get("banned"), banned);
            predicateList.add(predicates[0]);
        }
        if (minExperience != null) {
            predicates[0] = cb.greaterThanOrEqualTo(from.get("experience"), minExperience);
            predicateList.add(predicates[0]);
        }
        if (maxExperience != null) {
            predicates[0] = cb.lessThanOrEqualTo(from.get("experience"), maxExperience);
            predicateList.add(predicates[0]);
        }
        if (minLevel != null) {
            predicates[0] = cb.greaterThanOrEqualTo(from.get("level"), minLevel);
            predicateList.add(predicates[0]);
        }
        if (maxLevel != null) {
            predicates[0] = cb.lessThanOrEqualTo(from.get("level"), maxLevel);
            predicateList.add(predicates[0]);
        }
        Predicate[] objects = predicateList.toArray(new Predicate[0]);
        CriteriaQuery<Player> where = query.select(from).where(objects);


        List<Player> resultList = manager.createQuery(where).getResultList();

        return resultList;
    }

    public List<Player> getPlayerAll(String name, String title, Race race, Profession profession, Long before, Long after,
                                     Boolean banned, Integer minExperience, Integer maxExperience,
                                     Integer minLevel, Integer maxLevel, PlayerOrder order, Integer pageNumber, Integer pageSize) {


        EntityManager manager = entityManagerFactory.createEntityManager();
        manager.getTransaction().begin();
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<Player> query = cb.createQuery(Player.class);
        Root<Player> from = query.from(Player.class);

        List<Predicate> predicateList = new ArrayList<>();
        Predicate[] predicates = new Predicate[1];
        if (name != null) {
            predicates[0] = cb.like(from.get("name"), "%" + name + "%");
            predicateList.add(predicates[0]);
        }
        if (title != null) {
            predicates[0] = cb.like(from.get("title"), "%" + title + "%");
            predicateList.add(predicates[0]);
        }
        if (race != null) {
            predicates[0] = cb.equal(from.get("race"), race);
            predicateList.add(predicates[0]);
        }
        if (profession != null) {
            predicates[0] = cb.equal(from.get("profession"), profession);
            predicateList.add(predicates[0]);
        }
        if (before != null) {
            predicates[0] = cb.greaterThanOrEqualTo(from.get("birthday"), new Date(before));
            predicateList.add(predicates[0]);
        }
        if (after != null) {
            predicates[0] = cb.lessThanOrEqualTo(from.get("birthday"), new Date(after));
            predicateList.add(predicates[0]);
        }
        if (banned != null) {
            predicates[0] = cb.equal(from.get("banned"), banned);
            predicateList.add(predicates[0]);
        }
        if (minExperience != null) {
            predicates[0] = cb.greaterThanOrEqualTo(from.get("experience"), minExperience);
            predicateList.add(predicates[0]);
        }
        if (maxExperience != null) {
            predicates[0] = cb.lessThanOrEqualTo(from.get("experience"), maxExperience);
            predicateList.add(predicates[0]);
        }
        if (minLevel != null) {
            predicates[0] = cb.greaterThanOrEqualTo(from.get("level"), minLevel);
            predicateList.add(predicates[0]);
        }
        if (maxLevel != null) {
            predicates[0] = cb.lessThanOrEqualTo(from.get("level"), maxLevel);
            predicateList.add(predicates[0]);
        }
        Predicate[] objects = predicateList.toArray(new Predicate[0]);
        CriteriaQuery<Player> where = query.select(from).where(objects);


        TypedQuery<Player> query1 = manager.createQuery(where);
        if (order == null) {
            order = PlayerOrder.ID;
        }
        if (pageNumber == null) {
            pageNumber = 0;
        }
        if (pageSize == null) {
            pageSize = 3;
        }
        if (pageNumber == 0) {
            query1.setMaxResults(pageSize);
        }

        if (pageNumber != 0) {
            if (pageNumber != 1) {
                query1.setMaxResults(pageNumber * pageSize).setFirstResult((pageNumber - 1) * pageSize);
            }
            query1.setMaxResults(pageNumber * pageSize).setFirstResult((pageNumber) * pageSize);
        }
        List<Player> resultList = query1.getResultList();

        return resultList;
    }

    public Player getById(Long id) {
        Optional<Player> optionalPlayer = playerRepository.findById(id);
        Player player = null;
        try {
            player = optionalPlayer.get();
        } catch (Exception exception) {
        }
        return player;
    }

    public void add(Player player) {
        player.setLevel(player.getLevel());
        player.setUntilNextLevel(player.getUntilNextLevel());
        playerRepository.save(player);
    }

    public void update(Player player) {
        player.setLevel(player.getLevel());
        player.setUntilNextLevel(player.getUntilNextLevel());
        playerRepository.save(player);
    }

    public void delete(Long id) {
        playerRepository.deleteById(id);
    }
    public static boolean isPlayerParamsNull(Player player) {
        return player.getName() == null && player.getTitle() == null && player.getRace() == null &&
                player.getProfession() == null && player.getBirthday() == null && player.getBanned() == null
                && player.getExperience() == null;
    }

    public static boolean canCreate(Player player) {
        return !(player.getName() == null || player.getTitle() == null || player.getRace() == null ||
                player.getProfession() == null || player.getBirthday() == null || player.getExperience() == null);
    }

    public static boolean isCorrectPlayer(Player player) {
        return !(!checkName(player.getName()) ||
                !checkTitle(player.getTitle()) ||
                checkBirthday(player.getBirthday()) ||
                checkExp(player.getExperience()));
    }

    public static boolean checkName(String name) {
        return name.length() < 12 || !name.matches("^\\s*$");
    }

    public static boolean checkTitle(String name) {
        return name.length() < 30;
    }

    public static boolean checkBirthday(Date date) {
        if (date == null) return false;
        long birthday = date.getTime();
        long start = new GregorianCalendar(2000, Calendar.JANUARY, 0).getTimeInMillis();
        long end = new GregorianCalendar(3000, Calendar.JANUARY, 0).getTimeInMillis();
        return birthday < 0 || (birthday < start || birthday > end);
    }

    public static boolean checkExp(Integer exp) {
        return (exp != null) && (exp <= 0 || exp >= 10000000);
    }
}

    /*
    @Autowired
    public PlayerService(PlayerRepositoryV2 playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<Player> findAll() {
        return playerRepository.findAll();
    }

    public Player findOne(Long id) {
        Optional<Player> foundPlayer = playerRepository.findById(id);
        return foundPlayer.orElse(null);
    }

 */
