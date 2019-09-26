INSERT INTO
  MACHINE_STATE (id, accept, decline, name, start, turing_machine)
VALUES
  (27, false, false, 'BACK_TO_FIRST_A_FROM_B', false, 1),
  (28, false, false, 'A_TO_X', true, 1),
  (29, false, false, 'BACK_TO_LAST_C', false, 1),
  (30, false, false, 'BACK_TO_FIRST_A_FROM_C', false, 1),
  (31, false, false, 'BACK_TO_FIRST_A_FROM_A', false, 1),
  (32, false, false, 'FORWARD_TO_C_END', false, 1),
  (33, false, false, 'TEST_FOR_ANY_A_OR_B', false, 1),
  (34, false, true,  'DECLINE', false, 1),
  (35, false, false, 'CHECK_FOR_C', false, 1),
  (36, true,  false, 'ACCEPT', false, 1),
  (37, false, false, 'FORWARD_TO_B', false, 1),
  (38, false, false, 'DELETE_C', false, 1);
