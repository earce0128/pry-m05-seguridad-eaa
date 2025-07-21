USE `bd_cotizaciones`;

-- -------------------------------------------------------------------
-- Datos de prueba para tabla `promocion`
-- -------------------------------------------------------------------

-- Insertar Promociones
INSERT INTO promocion (nombre, descripcion, fec_vigencia_desde, fec_vigencia_hasta) VALUES
('2x1 + 5% + 10%','pague 1 lleve 2 + 5% adicional + 10% adicional','2025-05-10', '2025-06-25'),
('30% + 20%','30% base + 20% adicional','2025-05-1', '2025-07-15'),
('tabla dsctos A','dsctos por cantidad del 0 al 12%','2025-05-02', '2025-06-02');

-- Insertar Detalle de Promociones
INSERT INTO detalle_promocion (num_promocion, num_det_promocion, nombre, descripcion, es_base, lleve_n, pague_m, porc_dscto_plano, tipo_prom_acumulable, tipo_prom_base) VALUES
(1,1,'2x1','pague 1 lleve 2',1,2,1,NULL,NULL,'NXM'),
(1,2,'5%','5% adicional',0,NULL,NULL,5.0,'dscto-plano',NULL),
(1,3,'10%','10% adicional',0,NULL,NULL,10.0,'dscto-plano',NULL),
(2,1,'30% base','30% base',0,NULL,NULL,30.0,'dscto-plano',NULL),
(2,2,'20% adicional','20% adicional',0,NULL,NULL,20.0,'dscto-plano',NULL),
(3,1,'rango dsctos A','dsctos variables por rango',0,NULL,NULL,NULL,'dscto-cantidad',NULL);

-- Insertar Detalle de Promociones por cantidad
INSERT INTO detalle_promp_dscto_x_cant (num_promocion, num_det_promocion, num_dscto, cantidad, dscto) VALUES
(3,1,1,0,0.0),
(3,1,2,3,5.0),
(3,1,3,6,10.0),
(3,1,4,9,12.0);

-- -------------------------------------------------------------------
-- Datos de prueba para tabla `componente`
-- -------------------------------------------------------------------

INSERT INTO `componente`
  (`id_componente`, `descripcion`,         `marca`,    `modelo`,   `costo`,   `precio_base`, `tipo`,          `capacidad_alm`, `memoria`, `num_promocion`)
VALUES
  -- PCs
  ('PC01', 'Estación de trabajo básica',    'ACME',    'Work100',  500.00,    650.00,        'pc',            NULL,            '8GB',     NULL),
  ('PC02', 'PC para diseño gráfico',        'VisionX', 'GrafPro',  800.00,    1000.00,       'pc',            NULL,            '16GB',    NULL),
  ('PC03', 'Servidor ligero',               'DataSys', 'SrvLite',  700.00,    880.00,        'pc',            NULL,            '8GB',     NULL),
  ('PC04', 'PC gaming intermedio',          'GameTech','GT-500',   900.00,    1150.00,       'pc',            NULL,            '16GB',    NULL),
  ('PC05', 'Estación de edición de vídeo',  'VideoMax','EditPro', 1000.00,   1300.00,       'pc',            NULL,            '32GB',    NULL),

  -- Discos duros
  ('HD01', 'Disco interno SATA III',       'Seagate', 'Barracuda', 50.00,     70.00,        'disco-duro',    '1TB',           NULL,      NULL),
  ('HD02', 'Disco interno SATA III',       'Western', 'Blue200',   60.00,     85.00,        'disco-duro',    '2TB',           NULL,      NULL),
  ('HD03', 'Disco SSD NVMe',                'Samsung', '970Evo',   100.00,    140.00,        'disco-duro',    '500GB',         NULL,      NULL),
  ('HD04', 'Disco SSD NVMe',                'Kingston','A2000',    80.00,     110.00,        'disco-duro',    '1TB',           NULL,      NULL),
  ('HD05', 'Disco externo USB 3.0',         'Toshiba', 'Canvio',    70.00,     95.00,         'disco-duro',    '2TB',           NULL,      NULL),
  ('HD06', 'HDD empresarial',               'WD',      'RedPro',   120.00,    160.00,        'disco-duro',    '4TB',           NULL,      NULL),
  ('HD07', 'SSD empresarial',               'Intel',   'DC S3700', 200.00,    260.00,        'disco-duro',    '1.6TB',         NULL,      NULL),

  -- Tarjetas de video
  ('GPU01','Tarjeta gráfica básica',        'NVIDIA',  'GTX 1650', 150.00,    200.00,        'tarjeta-video', NULL,            '4GB',     1),
  ('GPU02','Tarjeta gráfica media',         'AMD',     'RX 580',   180.00,    240.00,        'tarjeta-video', NULL,            '8GB',     2),
  ('GPU03','Tarjeta gráfica alta',          'NVIDIA',  'RTX 3060', 350.00,    450.00,        'tarjeta-video', NULL,            '12GB',    3),
  ('GPU04','Tarjeta gráfica tope de gama',  'NVIDIA',  'RTX 4090',1500.00,   1800.00,       'tarjeta-video', NULL,            '24GB',    NULL),

  -- Monitores
  ('MON01','Monitor 24" Full HD',           'Dell',    'P2419H',    120.00,    160.00,        'monitor',       NULL,            NULL,      1),
  ('MON02','Monitor 27" QHD',               'LG',      '27QN600',   200.00,    260.00,        'monitor',       NULL,            NULL,      NULL),
  ('MON03','Monitor 32" 4K',                'Samsung', 'U32J590',   300.00,    380.00,        'monitor',       NULL,            NULL,      NULL),
  ('MON04','Monitor ultrawide 34"',         'ASUS',    'MX34VQ',    450.00,    600.00,        'monitor',       NULL,            NULL,      NULL);

-- -------------------------------------------------------------------
-- Datos de prueba para tabla `subcomponent_pc`
-- -------------------------------------------------------------------
-- PC01 integra HD01, GPU01, MON01
INSERT INTO `subcomponent_pc` (`id_componente_pc`,`id_sub_componente`,`cantidad`)
VALUES
  ('PC01','HD01',1),
  ('PC01','GPU01',1),
  ('PC01','MON01',1);

-- PC02 integra HD02, GPU02, MON02
INSERT INTO `subcomponent_pc` (`id_componente_pc`,`id_sub_componente`,`cantidad`)
VALUES
  ('PC02','HD02',1),
  ('PC02','GPU02',1),
  ('PC02','MON02',1);

-- PC03 integra HD03, GPU01, MON01
INSERT INTO `subcomponent_pc` (`id_componente_pc`,`id_sub_componente`,`cantidad`)
VALUES
  ('PC03','HD03',1),
  ('PC03','GPU01',1),
  ('PC03','MON01',1);

-- PC04 integra HD04, GPU03, MON03
INSERT INTO `subcomponent_pc` (`id_componente_pc`,`id_sub_componente`,`cantidad`)
VALUES
  ('PC04','HD04',1),
  ('PC04','GPU03',1),
  ('PC04','MON03',1);

-- PC05 integra HD05, GPU04, MON04
INSERT INTO `subcomponent_pc` (`id_componente_pc`,`id_sub_componente`,`cantidad`)
VALUES
  ('PC05','HD05',1),
  ('PC05','GPU04',1),
  ('PC05','MON04',1);
