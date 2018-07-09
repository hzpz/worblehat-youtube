package de.codecentric.psd.worblehat.domain;

import lombok.*;

import javax.annotation.Nonnull;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.io.Serializable;
import java.util.Date;

/**
 * Entity implementation class for Entity: Book
 */
@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Book implements Serializable {

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NonNull private String title;
	@NonNull private String author;
	@NonNull private String edition;

	// TODO: convert String to an ISBN class, that ensures a valid ISBN
	@NonNull private String isbn;
	@NonNull private Integer yearOfPublication;

	@OneToOne(mappedBy = "borrowedBook", orphanRemoval = true)
	@ToString.Exclude @EqualsAndHashCode.Exclude
	private Borrowing borrowing;

    @Column(columnDefinition = "TEXT")
    private String description;

	/**
	 * Creates a new book instance.
	 *
	 * @param bookParameter
	 */
	public Book(BookParameter bookParameter) {
		this(
				bookParameter.getTitle(),
				bookParameter.getAuthor(),
				bookParameter.getEdition(),
				bookParameter.getIsbn(),
				bookParameter.getYearOfPublication()
		);
		this.description = bookParameter.getDescription();
	}

	/*default*/ Book(Book book) {
		this(
				book.getTitle(),
				book.getAuthor(),
				book.getEdition(),
				book.getIsbn(),
				book.getYearOfPublication()
		);
	}

	boolean isSameCopy(@Nonnull Book book) {
		return getTitle().equals(book.title) && getAuthor().equals(book.author);
	}

	public void borrowNowByBorrower(String borrowerEmailAddress) {
		if (borrowing == null) {
            this.borrowing = new Borrowing(this, borrowerEmailAddress, new Date());
        }
	}

}
