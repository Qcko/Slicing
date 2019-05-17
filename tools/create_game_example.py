#!/usr/bin/python
# -*- coding: utf-8 -*-

import slicing_toolkit

game_topologies=(
    (3,'training01',5, u'DECRYPTED-ZPRÁVA-Dobrá_práce_vojíne._Ale_nic_o_čem_bys_měl_psát_domů.'),
    (4,'training02',10, u'DECRYPTED-ZPRÁVA-To_už_bylo_trochu_těžší_což?_Za_odměnu_si_dej_pivo_v_kantýně.'),
    (5,'training03',15, u'DECRYPTED-ZPRÁVA-Vidím_že_tě_to_baví._Ty_to_hochu_dotáhneš_daleko._Ale_nezapomeň_si_občas_dát_pauzu.'),
    (8,'training04',20, u'DECRYPTED-ZPRÁVA-Tebe_si_vojáku_zapamatujeme._Tak_zvědavého_a_odhodlaného_člověka_jen_tak_nenajdeme._Tvé_akce_budeme_pečlivě_pozorovat.'),
    (5,'hack01',10, u'DECRYPTED-KÓD_NAVÁDĚJÍCÍ_POSILY-81796571'),
    (4,'hack02',10, u'DECRYPTED-HESLO_K_DATABÁZI_PERSONÁLNÍHO_ODDĚLENÍ_KOLONIE_LAHU-58614583'),
    (5,'hack03',10, u'DECRYPTED-KÓD-33435297'),
    (6,'hack04',15, u'DECRYPTED-KÓD-91611123'),
    (3,'hack05',5,  u'DECRYPTED-OVLÁDACÍ_KÓD_KYBERJEDNOTKY_T-3000_G25-42315273'),
    (5,'hack06',10, u'DECRYPTED-KÓD-61438629'),
    (5,'hack07',10, u'DECRYPTED-KÓD-73319461'),
)

slicing_toolkit.create_game(game_topologies)

