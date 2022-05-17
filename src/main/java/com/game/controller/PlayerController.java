package com.game.controller;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/players")
public class PlayerController {
    // Класс отвечает за логику запросов http
    //Третий слой абстракции, после service. Работает с запросами и ответами

    @Autowired
    private PlayerService playerService;


    // Создание
    @PostMapping("/")
    public ResponseEntity<Player> createPlayer(@RequestBody Player player ) {
        if(!PlayerService.canCreate(player) || !PlayerService.isCorrectPlayer(player)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        playerService.add(player);
        return new ResponseEntity<>(player, HttpStatus.OK);
    }

    // Изменение
    @PostMapping("/{id}")
    public ResponseEntity<Player> updatePlayer(@RequestBody Player player, @PathVariable(value = "id")  long id  ) {
        if(id<=0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Player newPlayer = playerService.getById(id);
        if(PlayerService.isPlayerParamsNull(player)) {
            return new ResponseEntity<>(newPlayer,HttpStatus.OK);
        }
        if(newPlayer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if(PlayerService.checkExp(player.getExperience())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(PlayerService.checkBirthday(player.getBirthday())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if(!(player.getName() == null )) {
            newPlayer.setName(player.getName());
        }
        if(!(player.getTitle() == null)) {
            newPlayer.setTitle(player.getTitle());
        }
        if(!(player.getRace() == null)) {
            newPlayer.setRace(player.getRace());
        }
        if(!(player.getProfession() == null)) {
            newPlayer.setProfession(player.getProfession());
        }
        if(!(player.getBirthday() == null)) {
            newPlayer.setBirthday(player.getBirthday());
        }
        if(!(player.getBanned() == null)) {
            newPlayer.setBanned(player.getBanned());
        }
        if(!(player.getExperience() == null)) {
            newPlayer.setExperience(player.getExperience());
        }

        playerService.add(newPlayer);
        return new ResponseEntity<>(newPlayer,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Player> deletePlayerById(@PathVariable(value = "id") long id) {
        if(id<=0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(playerService.getById(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        playerService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    // Возвращение по id
    @GetMapping("/{id}")
    public ResponseEntity<Player> getPlayerById(@PathVariable(value = "id") long id) {
        if(id<=0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Player player = playerService.getById(id);
        if(player == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(player,HttpStatus.OK);
    }

    //Получить всех
    @GetMapping("")
    public ResponseEntity<List<Player>> getAllPlayer(@RequestParam(value = "name", required = false) String name,
                                                     @RequestParam(value = "title", required = false) String title,
                                                     @RequestParam(value = "race", required = false) Race race,
                                                     @RequestParam(value = "profession", required = false) Profession profession,
                                                     @RequestParam(value = "after", required = false) Long after,
                                                     @RequestParam(value = "before", required = false) Long before,
                                                     @RequestParam(value = "banned", required = false) Boolean banned,
                                                     @RequestParam(value = "minExperience", required = false) Integer minExperience,
                                                     @RequestParam(value = "maxExperience", required = false) Integer maxExperience,
                                                     @RequestParam(value = "minLevel", required = false) Integer minLevel,
                                                     @RequestParam(value = "maxLevel", required = false) Integer maxLevel,
                                                     @RequestParam(value = "order", required = false) PlayerOrder order,
                                                     @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                     @RequestParam(value = "pageSize", required = false) Integer pageSize) {

        List<Player> all = playerService.getPlayerAll(name,title,race,profession,after,before,banned,
                minExperience,maxExperience,minLevel,maxLevel,order,pageNumber,pageSize);
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    //Получить одного
    @GetMapping("/count")
    public ResponseEntity<Integer> getPlayersCount(@RequestParam(value = "name",required = false) String name,
                                                   @RequestParam(value = "title",required = false) String title,
                                                   @RequestParam(value = "race",required = false) Race race,
                                                   @RequestParam(value = "profession",required = false) Profession profession,
                                                   @RequestParam(value = "after",required = false) Long after,
                                                   @RequestParam(value = "before",required = false) Long before,
                                                   @RequestParam(value = "banned",required = false) Boolean banned,
                                                   @RequestParam(value = "minExperience",required = false) Integer minExperience,
                                                   @RequestParam(value = "maxExperience",required = false) Integer maxExperience,
                                                   @RequestParam(value = "minLevel",required = false) Integer minLevel,
                                                   @RequestParam(value = "maxLevel",required = false) Integer maxLevel
    ) {
        List<Player> all = playerService.getAll(name,title,race,profession,after,before,banned,minExperience,maxExperience,minLevel,maxLevel);
        return new ResponseEntity<>(all.size(),HttpStatus.OK);
    }

    /*
    @GetMapping
    public ResponseEntity getPlayers() {
        try {
            return ResponseEntity.ok("Server is working");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Fuck");
        }
        //Check server working Проверка работы сервера

    }
     */
}
