package com.ecf.zevent.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.stream.Stream;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ThematiqueType {
    JEUX_VIDEO_COMPETITIFS(1,"Jeux Vidéo Compétitifs", "Compétitions intenses de jeux vidéo en tête-à-tête ou en équipes."),
    SPEEDRUNNING(2, "Speedrunning", "Réalisation de jeux vidéo dans un temps record."),
    MOBA(3, "MOBA (Multiplayer Online Battle Arena)", "Jeux de stratégie en temps réel où des équipes de joueurs s'affrontent."),
    FPS(4, "FPS (First-Person Shooter)", "Jeux de tir à la première personne avec des compétitions rapides et dynamiques."),
    BATTLE_ROYALE(5, "Battle Royale", "Jeux où de nombreux joueurs s'affrontent jusqu'à ce qu'il ne reste qu'un seul survivant."),
    RTS(6, "RTS (Real-Time Strategy)", "Jeux de stratégie en temps réel où les joueurs contrôlent des armées et des ressources."),
    JEUX_DE_COMBAT(7, "Jeux de Combat", "Jeux où deux personnages s'affrontent dans des combats rapprochés."),
    JEUX_DE_CARTES_ET_DE_STRATEGIE(8, "Jeux de Cartes et de Stratégie", "Jeux où les joueurs utilisent des cartes ou des pions pour élaborer des stratégies."),
    SIMULATION_DE_SPORTS(9, "Simulation de Sports", "Jeux qui simulent des sports réels comme le football, le basketball, etc."),
    MMORPG(10,"MMORPG (Massively Multiplayer Online Role-Playing Game)", "Jeux de rôle en ligne massivement multijoueur où les joueurs explorent des mondes virtuels."),
    JEUX_DE_CONSTRUCTION_ET_CREATION(11, "Jeux de Construction et Création", "Jeux où les joueurs construisent et créent des structures et des mondes."),
    JEUX_D_AVENTURE_ET_DE_ROLE(12, "Jeux d'Aventure et de Rôle", "Jeux où les joueurs embarquent dans des quêtes et des aventures immersives."),
    JEUX_DE_COURSE(13, "Jeux de Course", "Compétitions de course automobile, moto ou autres véhicules."),
    JEUX_DE_PUZZLE_ET_DE_REFLEXION(14, "Jeux de Puzzle et de Réflexion", "Jeux qui mettent à l'épreuve les capacités de réflexion et de résolution de problèmes des joueurs."),
    SURVIVAL_HORROR(15, "Survival Horror", "Jeux où les joueurs doivent survivre dans des environnements horrifiques."),
    JEUX_DE_MUSIQUE_ET_DE_RYTHME(16, "Jeux de Musique et de Rythme", "Jeux où les joueurs suivent le rythme de la musique pour marquer des points.");


    private Integer key;
    private String label;
    private String description;

    ThematiqueType(Integer key, String label, String description){
        this.key = key;
        this.label = label;
        this.description = description;
    }

    public Integer getKey() {
        return key;
    }

    public String getLabel() {
        return label;
    }

    public String getDescription() {
        return description;
    }

    public static ThematiqueType getByLabel(String label) {
        return Stream.of(ThematiqueType.values()).filter(thematiqueType -> thematiqueType.getLabel().equals(label))
                .findFirst().get();
    }
}
