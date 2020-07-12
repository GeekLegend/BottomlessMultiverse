package fr.geeklegend.game;

import lombok.Getter;

public enum GameStage
{
    STAGE_1("stage1"),
    STAGE_2("stage2"),
    STAGE_3("stage3"),
    DEATH_MATCH("deathmatch");

    @Getter
    private String name;

    GameStage(String name)
    {
        this.name = name;
    }
}
