package me.m92.tatbook_web.userprofile;

import me.m92.tatbook_web.DomainEntity;
import me.m92.tatbook_web.converters.Base64EncoderDecoderConverter;

import javax.persistence.*;

/**
 * This entity shapes client's health conditions, but only these, which have or may have considerable impact on
 * tattooing process. Information about person's health are considered to be sensitive, therefore
 * every data is encoded before persisting to database.
 */
@Entity
@Table(name = "health_condition_declaration")
public class HealthCondition extends DomainEntity {

    @Id
    @TableGenerator(
            name = "health_condition_gen",
            table = "id_generator",
            pkColumnName = "generator_name",
            valueColumnName = "generator_val",
            pkColumnValue = "health_condition_declaration_id",
            initialValue = 500
    )
    @GeneratedValue(generator = "health_condition_gen")
    @Column(name = "health_condition_declaration_id")
    private Long id;

    @Lob
    @Convert(converter = Base64EncoderDecoderConverter.class)
    private String skinConditions;

    @Lob
    @Convert(converter = Base64EncoderDecoderConverter.class)
    private String circulatoryDisorders;

    @Lob
    @Convert(converter = Base64EncoderDecoderConverter.class)
    private String allergies;

    @Lob
    @Convert(converter = Base64EncoderDecoderConverter.class)
    private String otherMedicalConditions;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSkinConditions() {
        return skinConditions;
    }

    public void setSkinConditions(String skinConditions) {
        this.skinConditions = skinConditions;
    }

    public String getCirculatoryDisorders() {
        return circulatoryDisorders;
    }

    public void setCirculatoryDisorders(String circulatoryDisorders) {
        this.circulatoryDisorders = circulatoryDisorders;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public String getOtherMedicalConditions() {
        return otherMedicalConditions;
    }

    public void setOtherMedicalConditions(String otherMedicalConditions) {
        this.otherMedicalConditions = otherMedicalConditions;
    }
}
