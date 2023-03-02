package com.shaktipumps.shakti_rms.aryabata.classes;

import java.util.ArrayList;

public class Calibrations_Parameters {

    String parametersName;
    Integer paramId;

    public Calibrations_Parameters(Integer paramId, String parametersName) {
        this.parametersName = parametersName;
        this.paramId = paramId;
    }

    public String getParametersName() {
        return parametersName;
    }

    public void setParametersName(String parametersName) {
        this.parametersName = parametersName;
    }

    public Integer getParamId() {
        return paramId;
    }

    public void setParamId(Integer paramId) {
        this.paramId = paramId;
    }

    private static ArrayList<String> _defaultStrings;
    private static ArrayList<Calibrations_Parameters> _defaults;

    private static ArrayList<Calibrations_Parameters> Defaults() {
        if (_defaults == null) {
            _defaults = new ArrayList<>();
            _defaults.add(new Calibrations_Parameters(1, "RMS Voltage A"));
            _defaults.add(new Calibrations_Parameters(2, "RMS Voltage B"));
            _defaults.add(new Calibrations_Parameters(3, "RMS Voltage C"));
            _defaults.add(new Calibrations_Parameters(4, "RMS Current A"));
            _defaults.add(new Calibrations_Parameters(5, "RMS Current B"));
            _defaults.add(new Calibrations_Parameters(6, "RMS Current C"));
            _defaults.add(new Calibrations_Parameters(7, "Neutral Current"));
            _defaults.add(new Calibrations_Parameters(8, "Energy"));
           /* _defaults.add(new Calibrations_Parameters(9, "Future Provision 5"));
            _defaults.add(new Calibrations_Parameters(10, "Future Provision 6"));*/
            /*
              _defaults.add(new Calibrations_Parameters(11, "Future Provision 11"));
            _defaults.add(new Calibrations_Parameters(12, "Future Provision 12"));
            _defaults.add(new Calibrations_Parameters(13, "Future Provision 13"));
            _defaults.add(new Calibrations_Parameters(14, "Future Provision 14"));
            _defaults.add(new Calibrations_Parameters(15, "Future Provision 15"));
            _defaults.add(new Calibrations_Parameters(16, "Future Provision 16"));
            _defaults.add(new Calibrations_Parameters(17, "Future Provision 17"));
            _defaults.add(new Calibrations_Parameters(18, "Future Provision 18"));
            _defaults.add(new Calibrations_Parameters(19, "Future Provision 19"));
            _defaults.add(new Calibrations_Parameters(20, "Future Provision 20"));
            _defaults.add(new Calibrations_Parameters(21, "Future Provision 21"));
            _defaults.add(new Calibrations_Parameters(22, "Future Provision 22"));
            _defaults.add(new Calibrations_Parameters(23, "Future Provision 23"));
            _defaults.add(new Calibrations_Parameters(24, "Future Provision 24"));
            _defaults.add(new Calibrations_Parameters(25, "Future Provision 25"));
            _defaults.add(new Calibrations_Parameters(26, "Future Provision 26"));
            _defaults.add(new Calibrations_Parameters(27, "Future Provision 27"));
            _defaults.add(new Calibrations_Parameters(28, "Future Provision 28"));
            _defaults.add(new Calibrations_Parameters(29, "Future Provision 29"));
            _defaults.add(new Calibrations_Parameters(30, "Future Provision 30"));
            _defaults.add(new Calibrations_Parameters(31, "Future Provision 31"));
            _defaults.add(new Calibrations_Parameters(32, "Future Provision 32"));
            _defaults.add(new Calibrations_Parameters(33, "Future Provision 33"));
            _defaults.add(new Calibrations_Parameters(34, "Future Provision 34"));
            _defaults.add(new Calibrations_Parameters(35, "Future Provision 35"));
            _defaults.add(new Calibrations_Parameters(36, "Future Provision 36"));
            _defaults.add(new Calibrations_Parameters(37, "Future Provision 37"));
            _defaults.add(new Calibrations_Parameters(38, "Future Provision 38"));
            _defaults.add(new Calibrations_Parameters(39, "Future Provision 39"));
            _defaults.add(new Calibrations_Parameters(40, "Future Provision 40"));
            _defaults.add(new Calibrations_Parameters(41, "Future Provision 41"));
            _defaults.add(new Calibrations_Parameters(42, "Future Provision 42"));
            _defaults.add(new Calibrations_Parameters(43, "Future Provision 43"));
            _defaults.add(new Calibrations_Parameters(44, "Future Provision 44"));
            _defaults.add(new Calibrations_Parameters(45, "Future Provision 45"));
            _defaults.add(new Calibrations_Parameters(46, "Future Provision 46"));
            _defaults.add(new Calibrations_Parameters(47, "Future Provision 47"));
            _defaults.add(new Calibrations_Parameters(48, "Future Provision 48"));
            _defaults.add(new Calibrations_Parameters(49, "Future Provision 49"));
            _defaults.add(new Calibrations_Parameters(50, "Future Provision 50"));
            _defaults.add(new Calibrations_Parameters(51, "Future Provision 51"));
            _defaults.add(new Calibrations_Parameters(52, "Future Provision 52"));
            _defaults.add(new Calibrations_Parameters(53, "Future Provision 53"));
            _defaults.add(new Calibrations_Parameters(54, "Future Provision 54"));
            _defaults.add(new Calibrations_Parameters(55, "Future Provision 55"));
            _defaults.add(new Calibrations_Parameters(56, "Future Provision 56"));
            _defaults.add(new Calibrations_Parameters(57, "Future Provision 57"));
            _defaults.add(new Calibrations_Parameters(58, "Future Provision 58"));
            _defaults.add(new Calibrations_Parameters(59, "Future Provision 59"));
            _defaults.add(new Calibrations_Parameters(60, "Future Provision 60"));
            _defaults.add(new Calibrations_Parameters(61, "Future Provision 61"));
            _defaults.add(new Calibrations_Parameters(62, "Future Provision 62"));
            _defaults.add(new Calibrations_Parameters(63, "Future Provision 63"));
            _defaults.add(new Calibrations_Parameters(64, "Future Provision 64"));
            _defaults.add(new Calibrations_Parameters(65, "Future Provision 65"));
            _defaults.add(new Calibrations_Parameters(66, "Future Provision 66"));
            _defaults.add(new Calibrations_Parameters(67, "Future Provision 67"));
            _defaults.add(new Calibrations_Parameters(68, "Future Provision 68"));
            _defaults.add(new Calibrations_Parameters(69, "Future Provision 69"));
            _defaults.add(new Calibrations_Parameters(70, "Future Provision 70"));
            _defaults.add(new Calibrations_Parameters(71, "Future Provision 71"));
            _defaults.add(new Calibrations_Parameters(72, "Future Provision 72"));
            _defaults.add(new Calibrations_Parameters(73, "Future Provision 73"));
            _defaults.add(new Calibrations_Parameters(74, "Future Provision 74"));
            _defaults.add(new Calibrations_Parameters(75, "Future Provision 75"));
            _defaults.add(new Calibrations_Parameters(76, "Future Provision 76"));
            _defaults.add(new Calibrations_Parameters(77, "Future Provision 77"));
            _defaults.add(new Calibrations_Parameters(78, "Future Provision 78"));
            _defaults.add(new Calibrations_Parameters(79, "Future Provision 79"));
            _defaults.add(new Calibrations_Parameters(80, "Future Provision 80"));
            _defaults.add(new Calibrations_Parameters(81, "Future Provision 81"));
            _defaults.add(new Calibrations_Parameters(82, "Future Provision 82"));
            _defaults.add(new Calibrations_Parameters(83, "Future Provision 83"));
            _defaults.add(new Calibrations_Parameters(84, "Future Provision 84"));
            _defaults.add(new Calibrations_Parameters(85, "Future Provision 85"));
            _defaults.add(new Calibrations_Parameters(86, "Future Provision 86"));
            _defaults.add(new Calibrations_Parameters(87, "Future Provision 87"));
            _defaults.add(new Calibrations_Parameters(88, "Future Provision 88"));
            _defaults.add(new Calibrations_Parameters(89, "Future Provision 89"));
            _defaults.add(new Calibrations_Parameters(90, "Future Provision 90"));
            _defaults.add(new Calibrations_Parameters(91, "Future Provision 91"));
            _defaults.add(new Calibrations_Parameters(92, "Future Provision 92"));
            _defaults.add(new Calibrations_Parameters(93, "Future Provision 93"));
            _defaults.add(new Calibrations_Parameters(94, "Future Provision 94"));
            _defaults.add(new Calibrations_Parameters(95, "Future Provision 95"));
            _defaults.add(new Calibrations_Parameters(96, "Future Provision 96"));
            _defaults.add(new Calibrations_Parameters(97, "Future Provision 97"));
            _defaults.add(new Calibrations_Parameters(98, "Future Provision 98"));
            _defaults.add(new Calibrations_Parameters(99, "Future Provision 99"));
            _defaults.add(new Calibrations_Parameters(100, "Future Provision 100"));
            _defaults.add(new Calibrations_Parameters(101, "Future Provision 101"));
            _defaults.add(new Calibrations_Parameters(102, "Future Provision 102"));
            _defaults.add(new Calibrations_Parameters(103, "Future Provision 103"));
            _defaults.add(new Calibrations_Parameters(104, "Future Provision 104"));
            _defaults.add(new Calibrations_Parameters(105, "Future Provision 105"));
            _defaults.add(new Calibrations_Parameters(106, "Future Provision 106"));
            _defaults.add(new Calibrations_Parameters(107, "Future Provision 107"));
            _defaults.add(new Calibrations_Parameters(108, "Future Provision 108"));
            _defaults.add(new Calibrations_Parameters(109, "Future Provision 109"));
            _defaults.add(new Calibrations_Parameters(110, "Future Provision 110"));
            _defaults.add(new Calibrations_Parameters(111, "Future Provision 111"));
            _defaults.add(new Calibrations_Parameters(112, "Future Provision 112"));
            _defaults.add(new Calibrations_Parameters(113, "Future Provision 113"));
            _defaults.add(new Calibrations_Parameters(114, "Future Provision 114"));
            _defaults.add(new Calibrations_Parameters(115, "Future Provision 115"));
            _defaults.add(new Calibrations_Parameters(116, "Future Provision 116"));
            _defaults.add(new Calibrations_Parameters(117, "Future Provision 117"));
            _defaults.add(new Calibrations_Parameters(118, "Future Provision 118"));
            _defaults.add(new Calibrations_Parameters(119, "Future Provision 119"));
            _defaults.add(new Calibrations_Parameters(120, "Future Provision 120"));
            _defaults.add(new Calibrations_Parameters(121, "Future Provision 121"));
            _defaults.add(new Calibrations_Parameters(122, "Future Provision 122"));
            _defaults.add(new Calibrations_Parameters(123, "Future Provision 123"));
            _defaults.add(new Calibrations_Parameters(124, "Future Provision 124"));
            _defaults.add(new Calibrations_Parameters(125, "Future Provision 125"));
            _defaults.add(new Calibrations_Parameters(126, "Future Provision 126"));
            _defaults.add(new Calibrations_Parameters(127, "Future Provision 127"));
            _defaults.add(new Calibrations_Parameters(128, "Future Provision 128"));
            _defaults.add(new Calibrations_Parameters(129, "Future Provision 129"));
            _defaults.add(new Calibrations_Parameters(130, "Future Provision 130"));
            _defaults.add(new Calibrations_Parameters(131, "Future Provision 131"));
            _defaults.add(new Calibrations_Parameters(132, "Future Provision 132"));
            _defaults.add(new Calibrations_Parameters(133, "Future Provision 133"));
            _defaults.add(new Calibrations_Parameters(134, "Future Provision 134"));
            _defaults.add(new Calibrations_Parameters(135, "Future Provision 135"));
            _defaults.add(new Calibrations_Parameters(136, "Future Provision 136"));
            _defaults.add(new Calibrations_Parameters(137, "Future Provision 137"));
            _defaults.add(new Calibrations_Parameters(138, "Future Provision 138"));
            _defaults.add(new Calibrations_Parameters(139, "Future Provision 139"));
            _defaults.add(new Calibrations_Parameters(140, "Future Provision 140"));
            _defaults.add(new Calibrations_Parameters(141, "Future Provision 141"));
            _defaults.add(new Calibrations_Parameters(142, "Future Provision 142"));
            _defaults.add(new Calibrations_Parameters(143, "Future Provision 143"));
            _defaults.add(new Calibrations_Parameters(144, "Future Provision 144"));
            _defaults.add(new Calibrations_Parameters(145, "Future Provision 145"));
            _defaults.add(new Calibrations_Parameters(146, "Future Provision 146"));
            _defaults.add(new Calibrations_Parameters(147, "Future Provision 147"));
            _defaults.add(new Calibrations_Parameters(148, "Future Provision 148"));
            _defaults.add(new Calibrations_Parameters(149, "Future Provision 149"));
            _defaults.add(new Calibrations_Parameters(150, "Future Provision 150"));
            _defaults.add(new Calibrations_Parameters(151, "Future Provision 151"));
            _defaults.add(new Calibrations_Parameters(152, "Future Provision 152"));
            _defaults.add(new Calibrations_Parameters(153, "Future Provision 153"));
            _defaults.add(new Calibrations_Parameters(154, "Future Provision 154"));
            _defaults.add(new Calibrations_Parameters(155, "Future Provision 155"));
            _defaults.add(new Calibrations_Parameters(156, "Future Provision 156"));
            _defaults.add(new Calibrations_Parameters(157, "Future Provision 157"));
            _defaults.add(new Calibrations_Parameters(158, "Future Provision 158"));
            _defaults.add(new Calibrations_Parameters(159, "Future Provision 159"));
            _defaults.add(new Calibrations_Parameters(160, "Future Provision 160"));
            _defaults.add(new Calibrations_Parameters(161, "Future Provision 161"));
            _defaults.add(new Calibrations_Parameters(162, "Future Provision 162"));
            _defaults.add(new Calibrations_Parameters(163, "Future Provision 163"));
            _defaults.add(new Calibrations_Parameters(164, "Future Provision 164"));
            _defaults.add(new Calibrations_Parameters(165, "Future Provision 165"));
            _defaults.add(new Calibrations_Parameters(166, "Future Provision 166"));
            _defaults.add(new Calibrations_Parameters(167, "Future Provision 167"));
            _defaults.add(new Calibrations_Parameters(168, "Future Provision 168"));
            _defaults.add(new Calibrations_Parameters(169, "Future Provision 169"));
            _defaults.add(new Calibrations_Parameters(170, "Future Provision 170"));
            _defaults.add(new Calibrations_Parameters(171, "Future Provision 171"));
            _defaults.add(new Calibrations_Parameters(172, "Future Provision 172"));
            _defaults.add(new Calibrations_Parameters(173, "Future Provision 173"));
            _defaults.add(new Calibrations_Parameters(174, "Future Provision 174"));
            _defaults.add(new Calibrations_Parameters(175, "Future Provision 175"));
            _defaults.add(new Calibrations_Parameters(176, "Future Provision 176"));
            _defaults.add(new Calibrations_Parameters(177, "Future Provision 177"));
            _defaults.add(new Calibrations_Parameters(178, "Future Provision 178"));
            _defaults.add(new Calibrations_Parameters(179, "Future Provision 179"));
            _defaults.add(new Calibrations_Parameters(180, "Future Provision 180"));
            _defaults.add(new Calibrations_Parameters(181, "Future Provision 181"));
            _defaults.add(new Calibrations_Parameters(182, "Future Provision 182"));
            _defaults.add(new Calibrations_Parameters(183, "Future Provision 183"));
            _defaults.add(new Calibrations_Parameters(184, "Future Provision 184"));
            _defaults.add(new Calibrations_Parameters(185, "Future Provision 185"));
            _defaults.add(new Calibrations_Parameters(186, "Future Provision 186"));
            _defaults.add(new Calibrations_Parameters(187, "Future Provision 187"));
            _defaults.add(new Calibrations_Parameters(188, "Future Provision 188"));
            _defaults.add(new Calibrations_Parameters(189, "Future Provision 189"));
            _defaults.add(new Calibrations_Parameters(190, "Future Provision 190"));
            _defaults.add(new Calibrations_Parameters(191, "Future Provision 191"));
            _defaults.add(new Calibrations_Parameters(192, "Future Provision 192"));
            _defaults.add(new Calibrations_Parameters(193, "Future Provision 193"));
            _defaults.add(new Calibrations_Parameters(194, "Future Provision 194"));
            _defaults.add(new Calibrations_Parameters(195, "Future Provision 195"));
            _defaults.add(new Calibrations_Parameters(196, "Future Provision 196"));
            _defaults.add(new Calibrations_Parameters(197, "Future Provision 197"));
            _defaults.add(new Calibrations_Parameters(198, "Future Provision 198"));
            _defaults.add(new Calibrations_Parameters(199, "Future Provision 199"));
            _defaults.add(new Calibrations_Parameters(200, "Future Provision 200"));
            _defaults.add(new Calibrations_Parameters(201, "Future Provision 201"));
            _defaults.add(new Calibrations_Parameters(202, "Future Provision 202"));
            _defaults.add(new Calibrations_Parameters(203, "Future Provision 203"));
            _defaults.add(new Calibrations_Parameters(204, "Future Provision 204"));
            _defaults.add(new Calibrations_Parameters(205, "Future Provision 205"));
            _defaults.add(new Calibrations_Parameters(206, "Future Provision 206"));
            _defaults.add(new Calibrations_Parameters(207, "Future Provision 207"));
            _defaults.add(new Calibrations_Parameters(208, "Future Provision 208"));
            _defaults.add(new Calibrations_Parameters(209, "Future Provision 209"));
            _defaults.add(new Calibrations_Parameters(210, "Future Provision 210"));
            _defaults.add(new Calibrations_Parameters(211, "Future Provision 211"));
            _defaults.add(new Calibrations_Parameters(212, "Future Provision 212"));
            _defaults.add(new Calibrations_Parameters(213, "Future Provision 213"));
            _defaults.add(new Calibrations_Parameters(214, "Future Provision 214"));
            _defaults.add(new Calibrations_Parameters(215, "Future Provision 215"));
            _defaults.add(new Calibrations_Parameters(216, "Future Provision 216"));
            _defaults.add(new Calibrations_Parameters(217, "Future Provision 217"));
            _defaults.add(new Calibrations_Parameters(218, "Future Provision 218"));
            _defaults.add(new Calibrations_Parameters(219, "Future Provision 219"));
            _defaults.add(new Calibrations_Parameters(220, "Future Provision 220"));
            _defaults.add(new Calibrations_Parameters(221, "Future Provision 221"));
            _defaults.add(new Calibrations_Parameters(222, "Future Provision 222"));
            _defaults.add(new Calibrations_Parameters(223, "Future Provision 223"));
            _defaults.add(new Calibrations_Parameters(224, "Future Provision 224"));
            _defaults.add(new Calibrations_Parameters(225, "Future Provision 225"));
            _defaults.add(new Calibrations_Parameters(226, "Future Provision 226"));
            _defaults.add(new Calibrations_Parameters(227, "Future Provision 227"));
            _defaults.add(new Calibrations_Parameters(228, "Future Provision 228"));
            _defaults.add(new Calibrations_Parameters(229, "Future Provision 229"));
            _defaults.add(new Calibrations_Parameters(230, "Future Provision 230"));
            _defaults.add(new Calibrations_Parameters(231, "Future Provision 231"));
            _defaults.add(new Calibrations_Parameters(232, "Future Provision 232"));
            _defaults.add(new Calibrations_Parameters(233, "Future Provision 233"));
            _defaults.add(new Calibrations_Parameters(234, "Future Provision 234"));
            _defaults.add(new Calibrations_Parameters(235, "Future Provision 235"));
            _defaults.add(new Calibrations_Parameters(236, "Future Provision 236"));
            _defaults.add(new Calibrations_Parameters(237, "Future Provision 237"));
            _defaults.add(new Calibrations_Parameters(238, "Future Provision 238"));
            _defaults.add(new Calibrations_Parameters(239, "Future Provision 239"));
            _defaults.add(new Calibrations_Parameters(240, "Future Provision 240"));
            _defaults.add(new Calibrations_Parameters(241, "Future Provision 241"));
            _defaults.add(new Calibrations_Parameters(242, "Future Provision 242"));
            _defaults.add(new Calibrations_Parameters(243, "Future Provision 243"));
            _defaults.add(new Calibrations_Parameters(244, "Future Provision 244"));
            _defaults.add(new Calibrations_Parameters(245, "Future Provision 245"));
            _defaults.add(new Calibrations_Parameters(246, "Future Provision 246"));
            _defaults.add(new Calibrations_Parameters(247, "Future Provision 247"));
            _defaults.add(new Calibrations_Parameters(248, "Future Provision 248"));
            _defaults.add(new Calibrations_Parameters(249, "Future Provision 249"));
            _defaults.add(new Calibrations_Parameters(250, "Future Provision 250"));
            _defaults.add(new Calibrations_Parameters(251, "Future Provision 251"));
            _defaults.add(new Calibrations_Parameters(252, "Future Provision 252"));
            _defaults.add(new Calibrations_Parameters(253, "Future Provision 253"));
            _defaults.add(new Calibrations_Parameters(254, "Future Provision 254"));
            _defaults.add(new Calibrations_Parameters(255, "Future Provision 255"));
            */
        }
        return _defaults;
    }

    public static ArrayList<String> DefaultStrings() {
        try {

            if (_defaultStrings == null) {
                _defaultStrings = new ArrayList<>();
                for (Calibrations_Parameters each : Defaults()) {
                    _defaultStrings.add(each.getParametersName());
                }
            }
            return _defaultStrings;
        } catch (Exception ex) {
            return null;
        }
    }

    public static Calibrations_Parameters GetMatchingRecordForTheName(String name) {
        for (Calibrations_Parameters each : Defaults()) {
            if (each.getParametersName().equals(name)) {
                return each;
            }
        }
        return null;
    }

    public static Calibrations_Parameters GetMatchingRecordForTheId(int id) {
        for (Calibrations_Parameters each : Defaults()) {
            if (each.getParamId() == id) {
                return each;
            }
        }
        return null;
    }
}
