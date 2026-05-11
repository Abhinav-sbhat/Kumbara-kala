package com.kumbarakala.app.ui.viewmodel;

import android.content.Context;
import com.google.ai.client.generativeai.GenerativeModel;
import com.kumbarakala.app.data.repository.ArtisanRepository;
import com.kumbarakala.app.data.repository.AuthRepository;
import com.kumbarakala.app.data.repository.CardRepository;
import com.kumbarakala.app.data.repository.ProductRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class ProductDetailViewModel_Factory implements Factory<ProductDetailViewModel> {
  private final Provider<Context> contextProvider;

  private final Provider<ProductRepository> productRepositoryProvider;

  private final Provider<CardRepository> cardRepositoryProvider;

  private final Provider<ArtisanRepository> artisanRepositoryProvider;

  private final Provider<AuthRepository> authRepositoryProvider;

  private final Provider<GenerativeModel> generativeModelProvider;

  public ProductDetailViewModel_Factory(Provider<Context> contextProvider,
      Provider<ProductRepository> productRepositoryProvider,
      Provider<CardRepository> cardRepositoryProvider,
      Provider<ArtisanRepository> artisanRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider,
      Provider<GenerativeModel> generativeModelProvider) {
    this.contextProvider = contextProvider;
    this.productRepositoryProvider = productRepositoryProvider;
    this.cardRepositoryProvider = cardRepositoryProvider;
    this.artisanRepositoryProvider = artisanRepositoryProvider;
    this.authRepositoryProvider = authRepositoryProvider;
    this.generativeModelProvider = generativeModelProvider;
  }

  @Override
  public ProductDetailViewModel get() {
    return newInstance(contextProvider.get(), productRepositoryProvider.get(), cardRepositoryProvider.get(), artisanRepositoryProvider.get(), authRepositoryProvider.get(), generativeModelProvider.get());
  }

  public static ProductDetailViewModel_Factory create(Provider<Context> contextProvider,
      Provider<ProductRepository> productRepositoryProvider,
      Provider<CardRepository> cardRepositoryProvider,
      Provider<ArtisanRepository> artisanRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider,
      Provider<GenerativeModel> generativeModelProvider) {
    return new ProductDetailViewModel_Factory(contextProvider, productRepositoryProvider, cardRepositoryProvider, artisanRepositoryProvider, authRepositoryProvider, generativeModelProvider);
  }

  public static ProductDetailViewModel newInstance(Context context,
      ProductRepository productRepository, CardRepository cardRepository,
      ArtisanRepository artisanRepository, AuthRepository authRepository,
      GenerativeModel generativeModel) {
    return new ProductDetailViewModel(context, productRepository, cardRepository, artisanRepository, authRepository, generativeModel);
  }
}
