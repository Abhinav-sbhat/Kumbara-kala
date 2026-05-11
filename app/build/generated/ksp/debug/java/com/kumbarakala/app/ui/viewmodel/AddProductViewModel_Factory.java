package com.kumbarakala.app.ui.viewmodel;

import com.kumbarakala.app.data.repository.AuthRepository;
import com.kumbarakala.app.data.repository.ProductRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata
@QualifierMetadata
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
public final class AddProductViewModel_Factory implements Factory<AddProductViewModel> {
  private final Provider<ProductRepository> productRepositoryProvider;

  private final Provider<AuthRepository> authRepositoryProvider;

  public AddProductViewModel_Factory(Provider<ProductRepository> productRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    this.productRepositoryProvider = productRepositoryProvider;
    this.authRepositoryProvider = authRepositoryProvider;
  }

  @Override
  public AddProductViewModel get() {
    return newInstance(productRepositoryProvider.get(), authRepositoryProvider.get());
  }

  public static AddProductViewModel_Factory create(
      Provider<ProductRepository> productRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    return new AddProductViewModel_Factory(productRepositoryProvider, authRepositoryProvider);
  }

  public static AddProductViewModel newInstance(ProductRepository productRepository,
      AuthRepository authRepository) {
    return new AddProductViewModel(productRepository, authRepository);
  }
}
