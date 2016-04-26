package cz.muni.fi.pv243.jms;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class SampleItem implements Serializable {

    private String name;

}
