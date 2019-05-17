#!/usr/bin/python
# -*- coding: utf-8 -*-

# Purpose: Toolkit for Slicing app by Qcko. Functions for analysing node IDs, generating nodes, generating networks (scenarios).
# Usage: As needed, but to begin, look at create_game_example.py. Run, observe results, try them in slicing app.
# Author(s): gavincz
# License: LGPL copyleft

import re
import string
import random
import copy
import os

import sys
reload(sys)
sys.setdefaultencoding('utf8')

symmetric_chars = 'AHIMOTUVWXY'
vowel_chars = 'AEIOUY'
ambiguous_consonant_chars = 'BFLMPSVZ'
company_lang = {
    'Adascorp': {
        'ovladnuti': 'msf',    
        'stazeni_dat': 'wget',   
        'informace': 'ls-l',    
        'spusteni_prikazu': 'open',    
        'prohledani_okoli': 'nmap',    
    },
    'Aratech': {
        'ovladnuti': 'exploitator',
        'stazeni_dat': 'scp',
        'informace': 'alist',
        'spusteni_prikazu': 'clear',
        'prohledani_okoli': 'map',
    },
    'CEC': {
        'ovladnuti': 'pwn-or',
        'stazeni_dat': 'mv-sec',
        'informace': 'dir',
        'spusteni_prikazu': 'od',
        'prohledani_okoli': 'sec-scan',
    },
    'Czerka': {
        'ovladnuti': 'powaEx',
        'stazeni_dat': 'CPREM',
        'informace': 'll',
        'spusteni_prikazu': 's-entry',
        'prohledani_okoli': 'scan',
    },
    'GSI': {
        'ovladnuti': 'hydra',
        'stazeni_dat': 'get-rm',
        'informace': 'dir-a',
        'spusteni_prikazu': 'cmd',
        'prohledani_okoli': 'mapAll',
    },
    'UNKNOWN': {
        'ovladnuti': 'N/A',
        'stazeni_dat': 'N/A',
        'informace': 'N/A',
        'spusteni_prikazu': 'N/A',
        'prohledani_okoli': 'N/A',
    },
}

role_cmd = {
    'databaze': ['ovladnuti', 'stazeni_dat', 'prohledani_okoli', ],
    'ovladac': ['ovladnuti', 'informace', 'spusteni_prikazu', 'prohledani_okoli',],
    'honeypot': [],
    'bezny_uzel': ['ovladnuti', 'prohledani_okoli',],
    'terminal': ['prohledani_okoli',],
}

cmd_translate = {
    'ovladnuti': 'capture',
    'stazeni_dat': 'download',
    'prohledani_okoli': 'scan',
    'informace': 'info',
    'spusteni_prikazu': 'run',
}

role_translate = {
    'databaze': 'Database',
    'ovladac': 'Driver',
    'honeypot': 'Honeypot',
    'bezny_uzel': 'Normal',
    'terminal': 'Terminal',
}

# Check format of node ID, fail program, when ID is wrongly formatted
def check_id(id):
    assert len(id) == 8
    pattern = re.compile("^([A-Z]|[0-9]){8}$")
    assert pattern.match(id) != None
    assert [check_Adascorp(id), check_Aratech(id), check_CEC(id), check_Czerka(id), check_GSI(id)].count(True) == 1 or check_honeypot(id)
    assert [check_databaze(id), check_honeypot(id), check_ovladac(id), check_bezny_uzel(id), check_terminal(id)].count(True) == 1

# Check format of node ID, returns true/false
def check_id_bool(id):
    r = (len(id) == 8)
    pattern = re.compile("^([A-Z]|[0-9]){8}$")
    r = r and (pattern.match(id) != None)
    r = r and (([check_Adascorp(id), check_Aratech(id), check_CEC(id), check_Czerka(id), check_GSI(id)].count(True) == 1) or (check_honeypot(id)))
    r = r and ([check_databaze(id), check_honeypot(id), check_ovladac(id), check_bezny_uzel(id), check_terminal(id)].count(True) == 1)
    return r

# Get numeric part of ID
def get_id_num(id):
    pattern = re.compile("[0-9]+")
    id_num = ''.join(pattern.findall(id))
    return id_num

# Get character part of ID
def get_id_str(id):
    pattern = re.compile("[A-Z]+")
    id_str = ''.join(pattern.findall(id))
    return id_str

def check_Adascorp(id):
    id_num = get_id_num(id)
    return len(id_num) > 2 and id_num[-3] < id_num[-2] < id_num[-1]

def check_Aratech(id):
    id_num = get_id_num(id)
    return len(id_num) > 2 and id_num[1] == '2' and id_num[-1] == '7'

def check_CEC(id):
    return id[2] == '2' and id[4] == '8'

def check_Czerka(id):
    id_num = get_id_num(id)
    return int(id_num) % 3 == 0

def check_GSI(id):
    id_num = get_id_num(id)
    return len(id_num) > 2 and id_num[0] > id_num[1] > id_num[2]

def check_databaze(id):
    id_num = get_id_num(id)
    id_str = get_id_str(id)
    return len(id_str) > 0 and id_str[0] in symmetric_chars and int(id_num) % 13 != 0

def check_honeypot(id):
    id_num = get_id_num(id)
    id_str = get_id_str(id)
    if len(id_str) > 0:
        return id_str[0] in symmetric_chars and int(id_num) % 13 == 0
    else:
        return int(id_num) % 13 == 0

def check_ovladac(id):
    id_str = get_id_str(id)
    return len(id_str) > 1 and id_str[1] in vowel_chars

def check_bezny_uzel(id):
    id_str = get_id_str(id)
    return len(id_str) > 0 and id_str[-1] in ambiguous_consonant_chars

def check_terminal(id):
    id_str = get_id_str(id)
    return len(id_str) > 0 and id_str[0] == 'S'

def check_comp(id):
    result = None
    id_num = get_id_num(id)
    id_str = get_id_str(id)
    if check_Adascorp(id):
        result = 'Adascorp'
    elif check_Aratech(id):
        result = 'Aratech'
    elif check_CEC(id):
        result = 'CEC'
    elif check_Czerka(id):
        result = 'Czerka'
    elif check_GSI(id):
        result = 'GSI'
    else:
        result = 'UNKNOWN'
#    assert result != None
    return result

def check_role(id):
    result = None
    id_num = get_id_num(id)
    id_str = get_id_str(id)
    if check_honeypot(id):
        result = 'honeypot'
    elif check_databaze(id):
        result = 'databaze'
    elif check_ovladac(id):
        result = 'ovladac'
    elif check_bezny_uzel(id):
        result = 'bezny_uzel'
    elif check_terminal(id):
        result = 'terminal'
    assert result != None
    return result

def get_commands(company, role):
    commands = []
    for cmd in role_cmd[role]:
        commands.append(company_lang[company][cmd])
    return commands

def analyze_id(id):
    check_id(id)
    company = check_comp(id)
    role = check_role(id)
    commands = get_commands(company, role)
    return (company, role, commands)

# Podminky
#Adascorp: num >= 3
#Aratech:  num >= 2
#CEC:      num >= 2
#Czerka:   num >= 1
#GSI:      num >= 3

#databaze: str >= 1
#ovladac:  str >= 2
#honeypot: str >= 1, num >= 2
#bezny_uzel: str >= 1
#terminal: str >= 1

# Generate ID candidate - might not be valid candidate - see usage in generate_id()
# Code is ugly and brute force, but works. Random black magick involved.
def generate_id_candidate(company, role):
    print('Genrating ID for {} - {}'.format(company, role))
    min_num_len = 0
    min_str_len = 0
    if company == 'Adascorp':
        min_num_len = 3
    if company == 'Aratech':
        min_num_len = 2
    if company == 'CEC':
        min_num_len = 2
    if company == 'Czerka':
        min_num_len = 1
    if company == 'GSI':
        min_num_len = 3
    if role == 'databaze':
        min_str_len = 1
    if role == 'ovladac':
        min_str_len = 2
    if role == 'honeypot':
        min_num_len = 2
        min_str_len = 1
    if role == 'bezny_uzel':
        min_str_len = 1
    if role == 'terminal':
        min_str_len = 1

    free = 8 - min_num_len - min_str_len
    free_num = random.randint(1, free-1)
    free_str = free - free_num

    total_num = min_num_len + free_num
    total_str = min_str_len + free_str

    print('free = '+str(free))
    print('free_num = '+str(free_num))
    print('free_str = '+str(free_str))
    print('total_num = '+str(total_num))
    print('total_str = '+str(total_str))

    id_num = ''.join(random.sample(string.digits*total_num,total_num))
    id_str = ''.join(random.sample(string.ascii_uppercase*total_str,total_str))

    id_num_list = list(id_num)
    id_str_list = list(id_str)

    # Modify random parts
    if company == 'Adascorp':
        id_num_list[-3] = str(random.randint(0,7))
        id_num_list[-2] = str(random.randint(int(id_num_list[-3])+1,8))
        id_num_list[-1] = str(random.randint(int(id_num_list[-2])+1,9))
    if company == 'Aratech':
        id_num_list[1] = '2'
        id_num_list[-1] = '7'
#    if company == 'CEC':
#        min_num_len = 2
    if company == 'Czerka':
        while not int(id_num) % 3 == 0:
            id_num_list = random.sample(string.digits*free_num,free_num)
            id_num = ''.join(id_num_list)
    if company == 'GSI':
        id_num_list[0] = str(random.randint(3,9))
        id_num_list[1] = str(random.randint(2,int(id_num_list[0])-1))
        id_num_list[2] = str(random.randint(1,int(id_num_list[1])-1))
    if role == 'databaze':
        id_str_list[0] = str(random.sample(symmetric_chars,1)[0])
    if role == 'ovladac':
        id_str_list[1] = str(random.sample(vowel_chars,1)[0])
    if role == 'honeypot':
        id_str_list[0] = str(random.sample(symmetric_chars,1)[0])
        while not int(id_num) % 13 == 0:
            id_num_list = random.sample(string.digits*free_num,free_num)
            id_num = ''.join(id_num_list)
    if role == 'bezny_uzel':
        id_str_list[-1] = str(random.sample(ambiguous_consonant_chars,1)[0])
    if role == 'terminal':
        id_str_list[0] = 'S'
    
    id = '--------'
    id_list = list(id)
    a_num = 0
    a_str = 0
    for i in range(0,8):
        if company == 'CEC' and i == 2:
            id_list[2] = '2'
        elif company == 'CEC' and i == 4:
            id_list[4] = '8'
        else:
            if random.randint(1,8) < total_num:
                if a_num < len(id_num_list):
                    id_list[i] = id_num_list[a_num]
                    a_num = a_num + 1
                elif a_str < len(id_str_list):
                    id_list[i] = id_str_list[a_str]
                    a_str = a_str + 1
            else:
                if a_str < len(id_str_list):
                    id_list[i] = id_str_list[a_str]
                    a_str = a_str + 1
                elif a_num < len(id_num_list):
                    id_list[i] = id_num_list[a_num]
                    a_num = a_num + 1

    return ''.join(id_list)

# Generate id candidates, until the candidate fulfills all criteria, i.e. is well formed, of intended company and role
def generate_id(company, role):
    id_c = generate_id_candidate(company, role)
    print(id_c)
    while not check_id_bool(id_c) or (check_role(id_c) != role) or (check_comp(id_c) != company):
        id_c = generate_id_candidate(company, role)
        print(id_c)
    return id_c

# Generate unique ID of given company/role, that i not yet in id_list, and then add it to the list
def generate_unique_id(company, role, id_list=[]):
    id = generate_id(company, role)
    while id in id_list:
        id = generate_id(company, role)
    id_list.append(id)
    return id

# For each company/role combination, generate number of unique IDs (given num_of_ids)
def generate_id_stash(num_of_ids):
    stash={}
    company_list = company_lang.keys()
    company_list.remove('UNKNOWN')
    for company in company_list:
        stash[company] = {}
        for role in role_cmd.keys():
            stash[company][role] = []
            for i in range(0,num_of_ids):
                stash[company][role].append(generate_id(company, role))
    print(stash)
    return stash

# Generate network:
# Start node is terminal
# Finish node is database
# ? each node has max 2 edges
# all direct paths from start to finish has to be max traverse_len+traverse_delta
# blind alleys - traverse_ba_max
# honeypots could be only in blind alleys

used_network_id_list = []
last_used_id = 0

# Generate random network node of given role. When role is databaze, db_data applies
def generate_network_node(role, db_data='ENCRYPTED_DATA'):
    global used_network_id_list
    global last_used_id
    last_used_id = last_used_id + 1
    company_list = company_lang.keys()
    company_list.remove('UNKNOWN')
    company = company_list[random.randint(0,len(company_list)-1)]
    serial = generate_unique_id(company, role, used_network_id_list)
    if role == 'databaze':
        inventory = db_data
    else:
        inventory = 'null'
    moves = 'null'
    actions = []
    for cmd in role_cmd[role]:
        actions.append(cmd_translate[cmd]+company) 
    return {
            'company': company, 
            'role': role, 
            'id': last_used_id, 
            'serial': serial, 
            'inventory': inventory,
            'moves': moves,
            'actions': actions,
            'neighbours_down':[], 
            'neighbours_up': [],
        }

# returns random role for nodes in intermediate (i.e. 2..n-1) network levels 
def gen_role(purpose):
    intermediate_role_list = ['databaze', 'ovladac', 'bezny_uzel']
    if purpose == 'intermediate':
        role = intermediate_role_list[random.randint(0,len(intermediate_role_list)-1)]
    return role

# generate network
    # currently only one topology is implemented and it is binary graph:
    # - first node is terminal
    # - half levels is branchig out (each 1 node on n-th level is connected to unique 2 nodes on n+1 level)
    # - when number of levels is even, middle connection is one-to-one
    # - second half of levels is pruning the branches (each 2 nodes on n-th level are connected to 1 unique node on n+1 level)
    # - final node is database with predefined text
    # Note: no honeypots in in generated network in this version
    # Note: following args are ignored in current version:
    # - traverse_delta
    # - traverse_honeypot_max 

def generate_network(start_role,finish_role,traverse_len,traverse_delta=0,traverse_ba_max=2,traverse_honeypot_max=1,branching=2,db_data='ENCRYPTED_DATA'):
    global used_network_id_list
    global last_used_id
    last_used_id = 0
    ba_node_count = 0
    honeypot_count = 0
    level = 0
    network_list = []
    level_list = [generate_network_node(start_role),]
    network_list.append(level_list)
    print(network_list)
    for level in range(1,traverse_len):
        print('level = {}'.format(level))
        level_list = []
        print('network_list', network_list)
        prev_level_list = network_list[level-1]
        random.shuffle(prev_level_list)
        print('prev_level_list =', prev_level_list)
        for prev_node in prev_level_list:
            if (level == traverse_len/2) and (traverse_len % 2 == 0):
                node = generate_network_node(gen_role('intermediate'))
                node['neighbours_down'].append(prev_node['id'])
                prev_node['neighbours_up'].append(node['id'])
                level_list.append(node)
            elif level <= traverse_len/2:
                # branching out
                for i in range(0,branching):
                    node = generate_network_node(gen_role('intermediate'))
                    node['neighbours_down'].append(prev_node['id'])
                    print('prev_node["neighbours_up"] = ', prev_node['neighbours_up'])
                    prev_node['neighbours_up'].append(node['id'])
                    print('prev_node["neighbours_up"] = ', prev_node['neighbours_up'])
                    print('network_list = ', network_list)
                    level_list.append(node)
            else:
                # pruning
                if prev_level_list.index(prev_node) % branching == 0:
                    if level == traverse_len-1:
                        node = generate_network_node(finish_role,db_data=db_data)
                        while check_honeypot(node['serial']):
                            node = generate_network_node(finish_role,db_data=db_data)
                    else:
                        node = generate_network_node(gen_role('intermediate'))
                    level_list.append(node)
                node['neighbours_down'].append(prev_node['id'])
                prev_node['neighbours_up'].append(node['id'])
            print('New level {}: {} '.format(level,level_list))
        network_list.append(level_list)
    return network_list

def get_id_by_serial(flat_network_list, serial):
    for node in flat_network_list:
        if node['serial'] == serial:
            return node['id']

# print network to scenario txt file
def print_network(network_list, f_name):
    count = 0
    flat_network_list = []
    f = open(f_name,"w")
    f_help_name = 'help_'+f_name
    f_help = open(f_help_name,'w')
    header = 'selector: ID,serialNumber,Inventory%,Moves%,AvailableActions%,NeighboursString%ID\n'
    f_help.write("all show-map\n")
    print(header)
    f.write(header)
    for level_list in network_list:
        for node in level_list:
            flat_network_list.append(node)
    for node in flat_network_list:
        neighbour_list = [ str(x) for x in node['neighbours_down']+node['neighbours_up'] ]
        line = '{}:{},{},{},{},{},{}\n'.format(role_translate[node['role']], str(node['id']), node['serial'], node['inventory'].decode('utf-8'), node['moves'], '%'.join(node['actions']), '%'.join(neighbour_list))
        print(line)
        f.write(line)
        help_line = node['serial']+' : '+str(analyze_id(node['serial']))+'\n'
        f_help.write(help_line)
    f.close()
 

def create_game(game_topologies=()):
    # creates following dir/file structure: 
    # ./<name of network>/
    #    - Slicing/
    #        - whichScenario.txt: refers <name of network>.txt
    #        - scenarios/
    #            - <name of network>.txt: contains generated network
    # ./help_<name of network>.txt: cheatsheet for given network for easy verification
    # The Slicing subdir can be simply copied to top level Slicing dir of application and all works as intended.
    # Each record in game_topologies represents one network to be created defined as following:
    # (<levels of network>,<name of network>,<minutes to slice>, <data in final database node>)
    # Example of input:
    # game_topologies=(
    #     (3,'training01',5, u'DECRYPTED-ZPRÁVA-Dobrá_práce_vojíne._Ale_nic_o_čem_bys_měl_psát_domů.'),
    #     (4,'training02',10, u'DECRYPTED-ZPRÁVA-To_už_bylo_trochu_těžší_což?_Za_odměnu_si_dej_pivo_v_kantýně.'),
    #     (5,'training03',15, u'DECRYPTED-ZPRÁVA-Vidím_že_tě_to_baví._Ty_to_hochu_dotáhneš_daleko._Ale_nezapomeň_si_občas_dát_pauzu.'),
    #     (8,'training04',20, u'DECRYPTED-ZPRÁVA-Tebe_si_vojáku_zapamatujeme._Tak_zvědavého_a_odhodlaného_člověka_jen_tak_nenajdeme._Tvé_akce_budeme_pečlivě_pozorovat.'),
    #     (5,'hack01',10, u'DECRYPTED-KÓD_NAVÁDĚJÍCÍ_POSILY-81796571'),
    #     (4,'hack02',10, u'DECRYPTED-HESLO_K_DATABÁZI_PERSONÁLNÍHO_ODDĚLENÍ_KOLONIE_LAHU-58614583'),
    #     (5,'hack03',10, u'DECRYPTED-KÓD-33435297'),
    #     (6,'hack04',15, u'DECRYPTED-KÓD-91611123'),
    #     (3,'hack05',5,  u'DECRYPTED-OVLÁDACÍ_KÓD_KYBERJEDNOTKY_T-3000_G25-42315273'),
    #     (5,'hack06',10, u'DECRYPTED-KÓD-61438629'),
    #     (5,'hack07',10, u'DECRYPTED-KÓD-73319461'),
    # )
    for topology in game_topologies:
        slicing_path = topology[1]+'/Slicing'
        scenario_path = slicing_path+'/scenarios'
        if not os.path.exists(slicing_path):
            os.makedirs(slicing_path)
        if not os.path.exists(scenario_path):
            os.makedirs(scenario_path)
        net = generate_network('terminal','databaze',topology[0],db_data=topology[3])
        f_name = topology[1]+'.txt'
        print_network(net, f_name)
        os.rename(f_name, scenario_path+'/'+f_name)
        f_ws = open('whichScenario.txt','w')
        header = "selector: duration : nameOfScenarioFile : worldFlavorTextFile\n"
        f_ws.write(header)
        line = "This: {} : {} : starwars.txt\n".format(topology[2]*60, f_name)
        f_ws.write(line)
        os.rename('whichScenario.txt', slicing_path+'/whichScenario.txt')

