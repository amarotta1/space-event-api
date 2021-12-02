package ar.edu.um.isa.space.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ManufacturerMapperTest {

    private ManufacturerMapper manufacturerMapper;

    @BeforeEach
    public void setUp() {
        manufacturerMapper = new ManufacturerMapperImpl();
    }
}
